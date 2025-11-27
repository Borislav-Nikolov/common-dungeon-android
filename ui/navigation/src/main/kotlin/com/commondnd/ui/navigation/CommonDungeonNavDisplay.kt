package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun CommonDungeonNavDisplay(
    currentGroup: NavGraphGroup,
    startDestination: NavGraphKey,
    entryProvider: NavigationScope.(NavGraphGroup) -> (NavKey) -> NavEntry<NavKey>
) {

    val backStack = rememberNavBackStack(startDestination)
    val navigationScope = remember(backStack) {
        object : NavigationScope {
            override fun navigate(destination: NavGraphKey): Boolean {
                return backStack.add(destination)
            }

            override fun navigateBack(): NavGraphKey? {
                return backStack.removeLastOrNull() as NavGraphKey?
            }
        }
    }
    val entryProvider = remember(currentGroup, navigationScope) {
        navigationScope.entryProvider(currentGroup)
    }
    NavDisplay(
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = {
            navigationScope.navigateBack()
        },
        backStack = backStack,
        entryProvider = entryProvider
    )
}

interface NavigationScope {

    fun navigate(destination: NavGraphKey): Boolean

    fun navigateBack(): NavGraphKey?
}
