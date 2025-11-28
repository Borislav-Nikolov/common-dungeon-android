package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun CommonDungeonNavDisplay(
    backStackController: BackStackController,
    registry: NavGraphRegistry.() -> Unit
) {
    val currentGroup by backStackController.currentGroup.collectAsState(null)
    if (currentGroup != null) {
        val registry = rememberNavRegistry()
        val provider = rememberNavProvider()
        val backStack = rememberBackStack(currentGroup!!, backStackController)
        val navController = rememberNavController(backStackController)
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
                    provider.get(currentGroup!!).forEach { (key, content) ->
                        entry(key = key, content = { content(it, navController) })
                    }
                }
            }
        )
    }
}

@Composable
private fun rememberNavProvider(): NavGraphProvider = remember { NavGraphProvider() }

@Composable
private fun rememberNavRegistry(): NavGraphRegistry = remember { NavGraphRegistry() }

@Composable
private fun rememberBackStack(
    currentGroup: Any,
    backStackController: BackStackController
    // TODO: save state in a better way; not using rememberNavBackStack because it requires a specific type (NavKey)
) = rememberSaveable(currentGroup, backStackController) { backStackController[currentGroup] }

@Composable
private fun rememberNavController(backStackController: BackStackController) =
    remember(backStackController) {
        object : NavController {
            override fun navigate(destination: Any): Boolean {
                return backStackController.push(destination)
            }

            override fun navigateBack(): Any? {
                return backStackController.pop()
            }
        }
    }

/**
 * Navigation controller interface for managing navigation actions within the navigation system.
 *
 * This interface provides methods to navigate between destinations and handle back navigation.
 * It is passed to composable destinations to allow them to trigger navigation actions.
 */
interface NavController {

    /**
     * Navigates to the specified destination.
     *
     * The destination will be added to the back stack, and the corresponding composable
     * content will be displayed. The destination must be registered in the current navigation
     * group's registry.
     *
     * @param destination The destination identifier to navigate to. This should match a key
     * registered in the [NavGraphRegistry] for the current group.
     * @return `true` if the navigation was successful, `false` otherwise.
     */
    fun navigate(destination: Any): Boolean

    /**
     * Navigates back to the previous destination in the back stack.
     *
     * Removes the current destination from the back stack and returns to the previous one.
     * If there are no more destinations in the back stack, this operation may fail or return null.
     *
     * @return The destination that was removed from the back stack, or `null` if the back stack
     * was empty or the operation failed.
     */
    fun navigateBack(): Any?
}
