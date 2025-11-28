package com.commondnd.ui.login

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object LoginScreen {

    const val Login = "Login"
}

fun NavGraphRegistry.registerLoginScreens() {
    register(
        group = CommonNavigationGroup.NoUser,
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
