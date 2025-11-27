package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable

interface NavGraphRegistry {

    fun register(group: Any, key: Any, content: @Composable (NavController) -> Unit)

    companion object {

        operator fun invoke(): NavGraphRegistry = DefaultNavGraphRegistry
    }
}

internal interface NavGraphProvider {

    fun get(group: Any): Map<Any, @Composable (NavController) -> Unit>

    companion object {

        operator fun invoke(): NavGraphProvider = DefaultNavGraphRegistry
    }
}

private object DefaultNavGraphRegistry : NavGraphRegistry, NavGraphProvider {

    private val groupToEntries =
        mutableMapOf<Any, MutableMap<Any, @Composable (NavController) -> Unit>>()

    override fun register(
        group: Any,
        key: Any,
        content: @Composable (NavController) -> Unit
    ) {
        if (group in groupToEntries && key !in groupToEntries[group]!!) {
            groupToEntries[group]!![key] = content
        } else {
            groupToEntries[group] = mutableMapOf(key to content)
        }
    }

    override fun get(group: Any): Map<Any, @Composable (NavController) -> Unit> {
        require(group in groupToEntries) { "Group $group has not been registered." }
        return groupToEntries[group]!!
    }
}
