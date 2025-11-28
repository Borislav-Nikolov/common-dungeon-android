package com.commondnd.ui.initial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.commondnd.ui.login.LoginScreen
import com.commondnd.ui.login.registerLoginScreens
import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object InitialScreen {

    const val Splash = "Splash"
    const val Initial = "Initial"
    const val About = "About"
}

fun NavGraphRegistry.registerInitialScreens() {
    register(
        group = CommonNavigationGroup.Blank,
        key = InitialScreen.Splash,
        content = { key, navController ->
            // TODO:
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {  }
        }
    )
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
