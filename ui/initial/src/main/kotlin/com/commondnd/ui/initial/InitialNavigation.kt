package com.commondnd.ui.initial

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commondnd.ui.core.BrightDawnLoading
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
    onLoginRequest: (Uri, Uri, String) -> Unit,
    onViewPrivacyPolicy: () -> Unit
) {
    register(
        key = InitialScreen.Splash,
        content = { _, _ ->
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                BrightDawnLoading()
            }
        }
    )
    register(
        key = InitialScreen.Initial,
        content = { _, navController ->
            InitialScreen(
                modifier = Modifier
                    .systemBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp),
                onLoginClick = {
                    navController.push(LoginScreen.Login)
                },
                onViewPrivacyPolicy = onViewPrivacyPolicy,
                onAboutClick = {
                    navController.push(InitialScreen.About)
                }
            )
        }
    )
    register(
        key = InitialScreen.About,
        content = { _, navController ->
            AboutScreen(onBack = { navController.pop() })
        }
    )

    registerLoginScreens(
        loginController = loginController,
        onLoginRequest = onLoginRequest
    )
}
