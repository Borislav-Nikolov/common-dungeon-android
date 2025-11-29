package com.commondnd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: GroupedNavController,
    private val loginController: LoginController
) : ViewModel(), GroupedNavController by navController, LoginController by loginController {

    init {
        push(CommonNavigationGroup.Blank, CommonNavigationGroup.Blank.groupInitialScreen)
        viewModelScope.launch {
            user.distinctUntilChanged().collectLatest {
                makeCurrent(
                    if (it != null) {
                        CommonNavigationGroup.UserScoped.Home
                    } else {
                        CommonNavigationGroup.NoUser
                    },
                    removeOthers = true
                )
            }
        }
    }

    val user: Flow<User?>
        get() = userRepository.monitorUser()

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
}
