package com.commondnd.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commondnd.data.player.Player
import com.commondnd.data.player.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.commondnd.data.core.State
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private var job: Job? = null
    private val _playerDataState = MutableStateFlow<State<Player>>(State.None())

    fun monitorOwnPlayerData(): Flow<State<Player>> {
        job?.cancel()
        viewModelScope.launch {
            try {
                _playerDataState.update { State.Loading() }
                playerRepository.monitorOwnPlayerData().collectLatest { playerData ->
                    _playerDataState.update { State.Loaded(playerData) }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (exception: Exception) {
                _playerDataState.update { State.Error(exception) }
            }
        }
        return _playerDataState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}
