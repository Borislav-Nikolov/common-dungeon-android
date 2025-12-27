package com.commondnd.ui.more

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.ui.navigation.NavGraphRegistry

object MoreScreen {

    const val More = "More"
}

fun NavGraphRegistry.registerMoreScreens() {
    register(
        key = MoreScreen.More,
        content = { key, navController ->
            val viewModel: MoreViewModel = hiltViewModel()
            MoreScreen(
                username = viewModel.getUser()?.username,
                onLogout = { viewModel.logout() })
        }
    )
}
