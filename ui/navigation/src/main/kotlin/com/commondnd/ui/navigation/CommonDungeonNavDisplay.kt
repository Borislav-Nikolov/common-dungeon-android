package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun CommonDungeonNavDisplay(
    groupedNavController: GroupedNavController,
    registry: NavGraphRegistry.() -> Unit
) {
    val currentGroup by groupedNavController.currentGroup.collectAsState(null)
    val backStack by groupedNavController.currentBackStack.collectAsState(null)
    if (currentGroup != null && backStack != null) {
        val registry = rememberNavRegistry()
        val provider = rememberNavProvider()
        NavDisplay(
            entryDecorators = listOf(
                // Add the default decorators for managing scenes and saving state
                rememberSaveableStateHolderNavEntryDecorator(),
                // Then add the view model store decorator
                rememberViewModelStoreNavEntryDecorator()
            ),
            onBack = {
                groupedNavController.pop()
            },
            backStack = backStack!!,
            entryProvider = remember(currentGroup, registry, provider) {
                registry.registry()
                entryProvider {
                    provider.get(currentGroup!!).forEach { (key, content) ->
                        entry(key = key, content = { content(it, groupedNavController) })
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
