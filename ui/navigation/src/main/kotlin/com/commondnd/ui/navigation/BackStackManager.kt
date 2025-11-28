package com.commondnd.ui.navigation

interface BackStackManager {

    fun add(group: Any)
    fun push(group: Any, entry: Any)
    fun remove(group: Any)
    fun pop(group: Any, entry: Any)
}
