package com.commondnd.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.data.core.Rarity
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.ui.core.BrightDawnLoading
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.core.label
import com.commondnd.ui.navigation.NavGraphRegistry
import kotlin.math.exp

object HomeScreen {

    const val Home = "Home"
    const val ExchangeTokens = "ExchangeTokens"
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        modifier = Modifier.verticalScroll(rememberScrollState()),
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
            val viewModel: ExchangeTokensViewModel = hiltViewModel()
            val playerState by viewModel.playerDataState.collectAsState()
            val calculationState by viewModel.calculationResult.collectAsState()
            val conversionState by viewModel.conversionResult.collectAsState()
            when (val state = playerState) {
                is State.Error<Player> -> {
                    ErrorScreen(error = state.error)
                }
                is State.Loaded<Player> -> {
                    ExchangeTokensScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        player = state.value,
                        calculationState = calculationState,
                        conversionState = conversionState,
                        calculateTokenConversion = { from, to, value ->
                            viewModel.calculateTokenConversion(
                                from = from,
                                to = to,
                                value = value
                            )
                        },
                        doTokenConversion = { from, to, value ->
                            viewModel.doTokenConversion(
                                from = from,
                                to = to,
                                value = value
                            )
                        }
                    )
                }
                is State.Loading<Player>,
                is State.None<Player> -> BrightDawnLoading()
            }
        }
    )
}
