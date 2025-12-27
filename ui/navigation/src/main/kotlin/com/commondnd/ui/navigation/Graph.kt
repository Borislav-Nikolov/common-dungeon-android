package com.commondnd.ui.navigation

import androidx.compose.runtime.Composable

interface NavGraphRegistry {

    fun register(key: Any, content: @Composable (Any, NavController) -> Unit)

    companion object {

        operator fun invoke(): NavGraphRegistry = DefaultNavGraphRegistry
    }
}

internal interface NavGraphProvider {

    fun get(): Map<Any, @Composable (Any, NavController) -> Unit>

    companion object {

        operator fun invoke(): NavGraphProvider = DefaultNavGraphRegistry
    }
}

private object DefaultNavGraphRegistry : NavGraphRegistry, NavGraphProvider {

    private val entries = mutableMapOf<Any, @Composable (Any, NavController) -> Unit>()

    override fun register(
        key: Any,
        content: @Composable (Any, NavController) -> Unit
    ) {
        entries[key] = content
    }

    override fun get(): Map<Any, @Composable (Any, NavController) -> Unit> {
        return entries
    }
}
