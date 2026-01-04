package com.commondnd

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commondnd.data.core.State
import com.commondnd.data.core.Synchronizable
import com.commondnd.data.user.User
import com.commondnd.data.user.UserRepository
import com.commondnd.ui.characters.CharactersScreen
import com.commondnd.ui.home.HomeScreen
import com.commondnd.ui.initial.InitialScreen
import com.commondnd.ui.inventory.InventoryScreen
import com.commondnd.ui.login.LoginController
import com.commondnd.ui.more.MoreScreen
import com.commondnd.ui.navigation.GroupedNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: GroupedNavController,
    private val loginController: LoginController,
    private val synchronizables: Set<@JvmSuppressWildcards Synchronizable>
) : ViewModel(), GroupedNavController by navController, LoginController by loginController {

    val user: StateFlow<User?> = userRepository.monitorUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    val hasBottomNavigation: StateFlow<Boolean> = navController.currentBackStack.map {
        when(it?.lastOrNull()) {
            HomeScreen.Home,
            CharactersScreen.Characters,
            InventoryScreen.Inventory,
            MoreScreen.More -> true
            else -> false
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    private val _dataSyncState = MutableStateFlow<State<Unit>>(State.None())
    val dataSyncState: StateFlow<State<Unit>> = _dataSyncState

    init {
        push(CommonNavigationGroup.Blank, CommonNavigationGroup.Blank.groupInitialScreen)
        viewModelScope.launch {
            user.collectLatest {
                makeCurrent(
                    if (it != null || userRepository.getAsync() != null) {
                        CommonNavigationGroup.UserScoped.Home
                    } else {
                        CommonNavigationGroup.NoUser
                    },
                    removeOthers = true
                )
            }
        }
    }

    val navigationTabs: List<CommonNavigationGroup.UserScoped> = listOf(
        CommonNavigationGroup.UserScoped.Home,
        CommonNavigationGroup.UserScoped.Characters,
        CommonNavigationGroup.UserScoped.Inventory,
        CommonNavigationGroup.UserScoped.More
    )

    private val Any.groupInitialScreen: Any
        get() = when (this) {
            is CommonNavigationGroup -> when (this) {
                CommonNavigationGroup.Blank -> InitialScreen.Splash
                CommonNavigationGroup.NoUser -> InitialScreen.Initial
                CommonNavigationGroup.UserScoped.Home -> HomeScreen.Home
                CommonNavigationGroup.UserScoped.Characters -> CharactersScreen.Characters
                CommonNavigationGroup.UserScoped.Inventory -> InventoryScreen.Inventory
                CommonNavigationGroup.UserScoped.More -> MoreScreen.More
            }

            else -> IllegalStateException("Unhandled group $this")
        }

    override fun makeCurrent(group: Any, removeOthers: Boolean) {
        if (group !in this) {
            push(group, group.groupInitialScreen)
        }
        navController.makeCurrent(group, removeOthers)
    }

    fun syncData() {
        if (_dataSyncState.value is State.Loading) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _dataSyncState.update { State.Loading() }
            try {
                val syncedSuccessfully = awaitAll(
                    *synchronizables.map { async { it.synchronize() } }.toTypedArray()
                ).all { it }
                _dataSyncState.update {
                    if (syncedSuccessfully) {
                        State.Loaded(Unit)
                    } else {
                        State.Error(RuntimeException("Could not sync all data"))
                    }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (expected: Exception) {
                _dataSyncState.update { State.Error(expected) }
            }
        }
    }
}
