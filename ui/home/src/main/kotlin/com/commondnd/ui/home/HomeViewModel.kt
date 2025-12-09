package com.commondnd.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.data.player.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    playerRepository: PlayerRepository
) : ViewModel() {

    val playerDataState: StateFlow<State<Player>> = playerRepository
        .monitorOwnPlayerData()
        .map<Player, State<Player>> { State.Loaded(it) }
        .onStart { emit(State.Loading()) }
        .catch { emit(State.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = State.None()
        )
}
