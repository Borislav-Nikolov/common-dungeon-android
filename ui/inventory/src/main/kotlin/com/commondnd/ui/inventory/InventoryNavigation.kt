package com.commondnd.ui.inventory

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.ui.core.BrightDawnLoading
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.navigation.NavGraphRegistry

object InventoryScreen {

    const val Inventory = "Inventory"
}

fun NavGraphRegistry.registerInventoryScreens() {
    register(
        key = InventoryScreen.Inventory,
        content = { _, navController ->
            val viewModel: InventoryViewModel = hiltViewModel()
            val playerDataState by viewModel.playerDataState.collectAsState()
            when (val state = playerDataState) {
                is State.Error<Player> -> {
                    ErrorScreen(
                        error = state.error,
                        onBack = { navController.pop() }
                    )
                }
                is State.Loaded<Player> -> {
                    InventoryScreen(player = state.value)
                }
                is State.Loading<*>,
                is State.None<*> -> BrightDawnLoading()
            }
        }
    )
}
