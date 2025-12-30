package com.commondnd.ui.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.ui.core.BrightDawnLoading
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.navigation.NavGraphRegistry

object CharactersScreen {

    const val Characters = "Characters"
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphRegistry.registerCharactersScreens() {
    register(
        key = CharactersScreen.Characters,
        content = { _, navController ->
            val viewModel: CharactersViewModel = hiltViewModel()
            val playerDataState by viewModel.playerDataState.collectAsState()
            when (val state = playerDataState) {
                is State.Error<Player> -> {
                    ErrorScreen(
                        error = state.error,
                        onBack = { navController.pop() }
                    )
                }
                is State.Loaded<Player> -> {
                    CharactersScreen(
                        player = state.value,
                        onSettings = {

                        }
                    )
                }
                is State.Loading<*>,
                is State.None<*> -> BrightDawnLoading()
            }
        }
    )
}
