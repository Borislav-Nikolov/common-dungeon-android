package com.commondnd.ui.more

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.ui.navigation.NavGraphRegistry

object MoreScreen {

    const val More = "More"
}

fun NavGraphRegistry.registerMoreScreens() {
    register(
        key = MoreScreen.More,
        content = { _, _ ->
            val viewModel: MoreViewModel = hiltViewModel()
            MoreScreen(onLogout = { viewModel.logout() })
        }
    )
}
