package com.commondnd.ui.home

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.ui.navigation.NavGraphRegistry

object HomeScreen {

    const val Home = "Home"
}

fun NavGraphRegistry.registerHomeScreens() {

    register(
        key = HomeScreen.Home,
        content = { key, navController ->
            val viewModel: HomeViewModel = hiltViewModel()
            val playerDataState by viewModel.monitorOwnPlayerData().collectAsState(State.None())
            when (playerDataState) {
                is State.Error<*> -> {
                    Text("Error: ${(playerDataState as State.Error<*>).error}")
                }
                is State.Loaded<*> -> {
                    HomeScreen(
                        playerData = (playerDataState as State.Loaded<Player>).value
                    )
                }
                is State.Loading<*>,
                is State.None<*> -> CircularProgressIndicator()

            }
        }
    )
}
