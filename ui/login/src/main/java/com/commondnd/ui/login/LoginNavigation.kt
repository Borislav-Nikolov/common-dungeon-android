package com.commondnd.ui.login

import android.net.Uri
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import com.commondnd.data.authorization.OAuthConfiguration
import com.commondnd.data.authorization.buildAuthorizationUrl
import com.commondnd.data.authorization.generateCodeVerifier
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
            when (state) {
                LoginState.Uninitialized -> LaunchedEffect(Unit) {
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
                LoginState.AuthorizationCanceled -> LaunchedEffect(Unit) {
                    navController.pop()
                }
                is LoginState.AuthorizationError -> {
                    Text("Auth error ${(state as LoginState.AuthorizationError).error} TODO: show error and have retry logic")
                }
                is LoginState.AuthorizationRequesting -> CircularProgressIndicator()
                is LoginState.LoginError -> {
                    Text("Auth error ${(state as LoginState.LoginError).error} TODO: show error and have retry logic")
                }
                is LoginState.LoginStarted -> CircularProgressIndicator()
                LoginState.LoginSuccess -> {
                    /* NO-OP */
                }
            }
        }
    )
}
