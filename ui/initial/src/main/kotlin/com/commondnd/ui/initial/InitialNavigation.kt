package com.commondnd.ui.initial

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.commondnd.ui.login.LoginController
import com.commondnd.ui.login.LoginScreen
import com.commondnd.ui.login.registerLoginScreens
import com.commondnd.ui.navigation.NavGraphRegistry

object InitialScreen {

    const val Splash = "Splash"
    const val Initial = "Initial"
    const val About = "About"
}

fun NavGraphRegistry.registerInitialScreens(
    loginController: LoginController,
     onLoginRequest: (Uri, Uri, String) -> Unit
) {
    register(
        key = InitialScreen.Splash,
        content = { key, navController ->
            // TODO:
            Surface(
                modifier = Modifier.fillMaxSize()
            ) { }
        }
    )
    register(
        key = InitialScreen.Initial,
        content = { key, navController ->
            InitialScreen(
                onLoginClick = {
                    navController.push(LoginScreen.Login)
                },
                onAboutClick = {
                    navController.push(InitialScreen.About)
                }
            )
        }
    )
    register(
        key = InitialScreen.About,
        content = { key, navController ->
            AboutScreen()
        }
    )

    registerLoginScreens(
        loginController = loginController,
        onLoginRequest = onLoginRequest
    )
}
