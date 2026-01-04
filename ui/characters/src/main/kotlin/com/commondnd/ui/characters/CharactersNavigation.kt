package com.commondnd.ui.characters

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
            val statusChangeState by viewModel.statusChangeResult.collectAsState()
            val context = LocalContext.current
            LaunchedEffect(statusChangeState) {
                if (statusChangeState is State.Error) {
                    Toast.makeText(
                        context,
                        R.string.error_character_status_change_failed,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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
                        onChangeStatus = { status, character ->
                            viewModel.changeCharacterStatus(
                                status = status,
                                character = character
                            )
                        }
                    )
                }
                is State.Loading<*>,
                is State.None<*> -> BrightDawnLoading()
            }
        }
    )
}
