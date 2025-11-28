package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

/**
 * A composable navigation display that manages navigation state and navigation transitions
 * for a group of destinations.
 *
 * This component sets up a navigation system with automatic state saving, view model management,
 * and back stack handling. It uses the Navigation3 library's [NavDisplay] internally with
 * configured entry decorators for state preservation and lifecycle management.
 *
 * @param currentGroup The navigation group identifier. Destinations are organized by groups,
 * and only destinations belonging to this group will be available for navigation.
 * @param startDestination The initial destination to display when the nav display is first created.
 * This destination must be registered in the provided registry for the current group.
 * @param registry A lambda that configures the [NavGraphRegistry] by registering navigation
 * destinations for various groups. Use [NavGraphRegistry.register] to associate destinations
 * with their composable content.
 */
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
