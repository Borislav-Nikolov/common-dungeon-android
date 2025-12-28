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
import com.commondnd.ui.navigation.NavGraphRegistry

object HomeScreen {

    const val Home = "Home"
}

fun NavGraphRegistry.registerHomeScreens() {

    register(
        key = HomeScreen.Home,
        content = { key, navController ->
            val viewModel: HomeViewModel = hiltViewModel()
            val userState by viewModel.userState.collectAsState()
            val playerDataState by viewModel.playerDataState.collectAsState()
            when (val state = playerDataState) {
                is State.Error -> {
                    Text("Error: ${state.error}")
                }

                is State.Loaded -> {
                    val context = LocalContext.current
                    HomeScreen(
                        user = (userState as? State.Loaded)?.value,
                        playerData = state.value,
                        onExchangeTokens = {
                            Toast.makeText(
                                context,
                                "Exchange tokens it not yet implemented.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }

                is State.Loading,
                is State.None -> BrightDawnLoading()

            }
        }
    )
}
