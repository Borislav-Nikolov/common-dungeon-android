package com.commondnd.ui.initial

import com.commondnd.ui.login.LoginScreen
import com.commondnd.ui.login.registerLoginScreens
import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

internal object InitialScreen {

    const val Initial = "Initial"
    const val About = "About"
}

fun NavGraphRegistry.registerInitialScreens() {
    register(
        group = CommonNavigationGroup.NoUser,
        key = InitialScreen.Initial,
        content = { key, navController ->
            InitialScreen(
                onLoginClick = {
                    navController.navigate(LoginScreen.Login)
                },
                onAboutClick = {
                    navController.navigate(InitialScreen.About)
                }
            )
        }
    )
    register(
        group = CommonNavigationGroup.NoUser,
        key = InitialScreen.About,
        content = { key, navController ->
            AboutScreen()
        }
    )

    registerLoginScreens()
}
