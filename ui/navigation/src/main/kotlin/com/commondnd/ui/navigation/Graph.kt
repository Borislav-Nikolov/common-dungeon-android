package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable

/**
 * Registry interface for managing navigation destination registrations.
 *
 * This registry allows organizing navigation destinations into groups, where each destination
 * is identified by a unique key within its group and associated with composable content.
 * The registry is used by [CommonDungeonNavDisplay] to look up and display the appropriate
 * composable for each destination in the navigation back stack.
 */
interface NavGraphRegistry {

    /**
     * Registers a navigation destination with its associated composable content.
     *
     * Destinations are organized by group, allowing different navigation contexts to maintain
     * separate sets of destinations. Within a group, each key must be unique.
     *
     * @param group The group identifier that this destination belongs to. Multiple destinations
     * can belong to the same group.
     * @param key The unique identifier for this destination within the group. This is the value
     * passed to [NavController.navigate] to navigate to this destination.
     * @param content The composable content to display when this destination is active.
     * The lambda receives the destination key and a [NavController] for further navigation.
     */
    fun register(group: Any, key: Any, content: @Composable (Any, NavController) -> Unit)

    companion object {

        /**
         * Creates a new instance of [NavGraphRegistry].
         *
         * @return A registry instance that can be used to register navigation destinations.
         */
        operator fun invoke(): NavGraphRegistry = DefaultNavGraphRegistry
    }
}

internal interface NavGraphProvider {

    fun get(group: Any): Map<Any, @Composable (Any, NavController) -> Unit>

    companion object {

        operator fun invoke(): NavGraphProvider = DefaultNavGraphRegistry
    }
}

private object DefaultNavGraphRegistry : NavGraphRegistry, NavGraphProvider {

    private val groupToEntries =
        mutableMapOf<Any, MutableMap<Any, @Composable (Any, NavController) -> Unit>>()

    override fun register(
        group: Any,
        key: Any,
        content: @Composable (Any, NavController) -> Unit
    ) {
        if (group in groupToEntries && key !in groupToEntries[group]!!) {
            groupToEntries[group]!![key] = content
        } else {
            groupToEntries[group] = mutableMapOf(key to content)
        }
    }

    override fun get(group: Any): Map<Any, @Composable (Any, NavController) -> Unit> {
        require(group in groupToEntries) { "Group $group has not been registered." }
        return groupToEntries[group]!!
    }
}
