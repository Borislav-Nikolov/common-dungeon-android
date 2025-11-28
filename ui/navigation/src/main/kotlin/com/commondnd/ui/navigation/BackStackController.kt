package com.commondnd.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface BackStackController {

    val currentGroup: Flow<Any?>
    operator fun get(group: Any): List<Any>
    operator fun contains(group: Any): Boolean
    fun push(group: Any, entry: Any): Boolean
    fun push(entry: Any): Boolean
    fun remove(group: Any)
    fun pop(group: Any? = null): Any?
    fun makeCurrent(group: Any)
}

internal class DefaultBackStackController @Inject constructor() : BackStackController {

    private val backStacks = mutableMapOf<Any, MutableList<Any>>()
    private val _currentGroup: MutableStateFlow<Any?> = MutableStateFlow(null)
    override val currentGroup: Flow<Any?> = _currentGroup

    override operator fun get(group: Any): List<Any> {
        return requireNotNull(backStacks[group]) { "Group $group not found." }
    }

    override fun contains(group: Any): Boolean {
        return group in backStacks
    }

    override fun push(group: Any, entry: Any): Boolean {
        backStacks.computeIfAbsent(group) { mutableStateListOf() }
        backStacks[group]!!.add(entry)
        if (_currentGroup.value == null) {
            makeCurrent(group)
        }
        return true
    }

    override fun push(entry: Any): Boolean {
        return push(requireNotNull(_currentGroup.value), entry)
    }

    override fun remove(group: Any) {
        require(group in backStacks) { "Group $group doesn't exits." }
        require(group != _currentGroup.value) { "Cannot remove current group." }
        backStacks.remove(group)
    }

    override fun pop(group: Any?): Any? {
        if (group == null) {
            return backStacks[_currentGroup.value]?.removeLastOrNull()
        }
        require(group in backStacks) { "Group $group doesn't exits." }
        require(_currentGroup.value == group) { "You can only pop the current group. Current=${_currentGroup.value}. Given=$group" }
        return backStacks[group]!!.removeLastOrNull()
    }

    override fun makeCurrent(group: Any) {
        require(group in backStacks) { "Group $group doesn't exits." }
        require(_currentGroup.value != group) { "Current group and make current request group are the same: $group" }
        _currentGroup.update { group }
    }
}
