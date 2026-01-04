package com.commondnd.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commondnd.data.character.CharacterStatus
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.data.player.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _statusChangeResult = MutableStateFlow<State<Unit>>(State.None())
    val statusChangeResult: StateFlow<State<Unit>> = _statusChangeResult

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

    fun changeCharacterStatus(
        status: CharacterStatus,
        character: PlayerCharacter
    ) {
        viewModelScope.launch {
            _statusChangeResult.update { State.Loading() }
            try {
                if (playerRepository.changeCharacterStatus(
                    status = status,
                    character = character
                )) {
                    _statusChangeResult.update { State.Loaded(Unit) }
                } else {
                    _statusChangeResult.update { State.Error(RuntimeException("Failed to change character status.")) }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (expected: Exception) {
                _statusChangeResult.update { State.Error(expected) }
            }
        }
    }
}
