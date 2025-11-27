package com.commondnd.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavGraphGroup {

    @Serializable
    data object NoUserGroup : NavGraphGroup

    @Serializable
    data object UserGroup : NavGraphGroup
}

sealed interface NavGraphKey : NavKey {

    val group: NavGraphGroup

    @Serializable
    data object Initial : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.NoUserGroup
    }

    @Serializable
    data object Login : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.NoUserGroup
    }

    @Serializable
    data object About : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.NoUserGroup
    }

    @Serializable
    data object Home : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.UserGroup
    }

    @Serializable
    data object Characters : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.UserGroup
    }

    @Serializable
    data object Inventory : NavGraphKey {

        override val group: NavGraphGroup
            get() = NavGraphGroup.UserGroup
    }
}
