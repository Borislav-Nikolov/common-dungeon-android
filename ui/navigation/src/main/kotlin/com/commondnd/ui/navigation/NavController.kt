package com.commondnd.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface NavController {

    fun push(entry: Any): Boolean

    fun pop(): Any?
}

interface GroupedNavController : NavController {

    val currentGroup: Flow<Any?>
    val currentBackStack: Flow<List<Any>?>
    operator fun contains(group: Any): Boolean
    fun push(group: Any, entry: Any): Boolean
    fun makeCurrent(group: Any, removeOthers: Boolean = false)
}

internal class DefaultGroupedNavController @Inject constructor() : GroupedNavController {

    private val backStacks = mutableMapOf<Any, MutableList<Any>>()
    private val _currentGroup: MutableStateFlow<Any?> = MutableStateFlow(null)
    override val currentGroup: Flow<Any?> = _currentGroup
    private val _currentBackStack = MutableStateFlow<List<Any>?>(_currentGroup.value?.let { backStacks[it] })
    override val currentBackStack: Flow<List<Any>?> = _currentBackStack

    override fun contains(group: Any): Boolean {
        return group in backStacks
    }

    override fun push(group: Any, entry: Any): Boolean {
        backStacks.computeIfAbsent(group) { mutableStateListOf() }
        backStacks[group]!!.add(entry)
        _currentBackStack.update { backStacks[group]?.toList() }
        if (_currentGroup.value == null) {
            makeCurrent(group)
        }
        return true
    }

    override fun push(entry: Any): Boolean {
        return push(requireNotNull(_currentGroup.value), entry)
    }

    override fun pop(): Any? {
        val removed = backStacks[_currentGroup.value]?.removeLastOrNull()
        _currentBackStack.update { backStacks[_currentGroup.value]?.toList() }
        return removed
    }

    override fun makeCurrent(group: Any, removeOthers: Boolean) {
        require(group in backStacks) { "Group $group doesn't exits." }
        _currentGroup.update { group }
        _currentBackStack.update { backStacks[group]?.toList() }
        if (removeOthers) {
            val currentBackStack = backStacks[group]
            backStacks.clear()
            backStacks[group] = currentBackStack!!
        }
    }
}
