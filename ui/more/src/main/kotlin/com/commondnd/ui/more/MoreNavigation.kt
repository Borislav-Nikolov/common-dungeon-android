package com.commondnd.ui.more

import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object MoreScreen {

    const val More = "More"
}

fun NavGraphRegistry.registerMoreScreens() {
    register(
        group = CommonNavigationGroup.UserScoped.More,
        key = MoreScreen.More,
        content = { key, navController ->
            MoreScreen()
        }
    )
}
