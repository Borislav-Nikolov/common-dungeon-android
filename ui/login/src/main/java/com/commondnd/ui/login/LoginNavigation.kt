package com.commondnd.ui.login

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.ui.navigation.NavGraphRegistry

object LoginScreen {

    const val Login = "Login"
}

fun NavGraphRegistry.registerLoginScreens() {
    register(
        key = LoginScreen.Login,
        content = { key, navController ->
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                onLogIn = {
                    loginViewModel.login()
                }
            )
        }
    )
}
