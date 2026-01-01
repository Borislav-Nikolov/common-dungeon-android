package com.commondnd.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.data.core.State
import com.commondnd.ui.core.BrightDawnLoading
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.navigation.NavGraphRegistry

object HomeScreen {

    const val Home = "Home"
    const val ExchangeTokens = "ExchangeTokens"
}

fun NavGraphRegistry.registerHomeScreens() {

    register(
        key = HomeScreen.Home,
        content = { _, navController ->
            val viewModel: HomeViewModel = hiltViewModel()
            val userState by viewModel.userState.collectAsState()
            val playerDataState by viewModel.playerDataState.collectAsState()
            when (val state = playerDataState) {
                is State.Error -> {
                    ErrorScreen(
                        error = state.error,
                        onBack = { navController.pop() }
                    )
                }

                is State.Loaded -> {
                    HomeScreen(
                        user = (userState as? State.Loaded)?.value,
                        playerData = state.value,
                        onExchangeTokens = {
                            navController.push(HomeScreen.ExchangeTokens)
                        }
                    )
                }

                is State.Loading,
                is State.None -> BrightDawnLoading()
            }
        }
    )

    register(
        key = HomeScreen.ExchangeTokens,
        content = { _, navController ->

        }
    )
}
