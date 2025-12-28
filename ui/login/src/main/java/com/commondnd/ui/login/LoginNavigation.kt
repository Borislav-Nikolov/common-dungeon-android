package com.commondnd.ui.login

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.commondnd.data.authorization.OAuthConfiguration
import com.commondnd.data.authorization.buildAuthorizationUrl
import com.commondnd.data.authorization.generateCodeVerifier
import com.commondnd.ui.core.BrightDawnLoading
import com.commondnd.ui.navigation.NavGraphRegistry

object LoginScreen {

    const val Login = "Login"
}

fun NavGraphRegistry.registerLoginScreens(
    loginController: LoginController,
    onLoginRequest: (Uri, Uri, String) -> Unit
) {
    register(
        key = LoginScreen.Login,
        content = { key, navController ->
            val state by loginController.currentState.collectAsState(LoginState.Uninitialized)
            val startLogin = {
                val redirectUri = "commondndoauth://callback"
                val codeVerifier = generateCodeVerifier()
                onLoginRequest(
                    buildAuthorizationUrl(
                        OAuthConfiguration.Discord(
                            redirectUri = redirectUri,
                            codeVerifier = codeVerifier
                        )
                    ),
                    redirectUri.toUri(),
                    codeVerifier
                )
            }
            when (val currentState = state) {
                LoginState.Uninitialized -> LaunchedEffect(Unit) { startLogin() }
                LoginState.AuthorizationCanceled -> LaunchedEffect(Unit) {
                    navController.pop()
                }
                is LoginState.AuthorizationError -> LoginErrorScreen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    error = currentState.error,
                    onBack = { navController.pop() },
                    onRetry = { startLogin() }
                )
                is LoginState.AuthorizationRequesting -> BrightDawnLoading()
                is LoginState.LoginError -> LoginErrorScreen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    error = currentState.error,
                    onBack = { navController.pop() },
                    onRetry = { startLogin() }
                )
                is LoginState.LoginStarted -> BrightDawnLoading()
                LoginState.LoginSuccess -> {
                    /* NO-OP */
                }
            }
        }
    )
}
