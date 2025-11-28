package com.commondnd.ui.login

import androidx.lifecycle.viewmodel.compose.viewModel
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
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen()
        }
    )
}
