package com.commondnd

sealed interface CommonNavigationGroup {

    data object Blank : CommonNavigationGroup
    data object NoUser : CommonNavigationGroup

    sealed interface UserScoped : CommonNavigationGroup {

        data object Home : UserScoped
        data object Characters : UserScoped
        data object Inventory : UserScoped
        data object More : UserScoped
    }
}
