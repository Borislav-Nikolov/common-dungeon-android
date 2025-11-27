package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun CommonDungeonNavDisplay(
    currentGroup: Any,
    startDestination: Any,
    registry: NavGraphRegistry.() -> Unit
) {
    val registry = remember { NavGraphRegistry() }
    val provider = remember { NavGraphProvider() }
    // TODO: save state in a better way; not using rememberNavBackStack because it requires a specific type (NavKey)
    val backStack = rememberSaveable(currentGroup, startDestination) { mutableStateListOf(startDestination) }
    val navController = remember(backStack) {
        object : NavController {
            override fun navigate(destination: Any): Boolean {
                return backStack.add(destination)
            }

            override fun navigateBack(): Any? {
                return backStack.removeLastOrNull() as Any?
            }
        }
    }
    NavDisplay(
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = {
            navController.navigateBack()
        },
        backStack = backStack,
        entryProvider = remember(currentGroup, provider, navController) {
            registry.registry()
            entryProvider {
                provider.get(currentGroup).forEach { (key, content) ->
                    entry(key = key, content = { content(it, navController) })
                }
            }
        }
    )
}

interface NavController {

    fun navigate(destination: Any): Boolean

    fun navigateBack(): Any?
}
