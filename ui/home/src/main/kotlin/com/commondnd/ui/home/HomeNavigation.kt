package com.commondnd.ui.home

import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object HomeScreen {

    const val Home = "Home"
}

fun NavGraphRegistry.registerHomeScreens() {

    register(
        group = CommonNavigationGroup.UserScoped.Home,
        key = HomeScreen.Home,
        content = { key, navController ->
            HomeScreen()
        }
    )
}
