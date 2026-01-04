package com.commondnd.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commondnd.data.player.Player
import com.commondnd.data.player.PlayerRepository
import com.commondnd.data.core.State
import com.commondnd.data.item.InventoryItem
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
class InventoryViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _operationState = MutableStateFlow<State<Unit>>(State.None())
    val operationState: StateFlow<State<Unit>> = _operationState

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

    fun sellItem(item: InventoryItem) {
        executeOperation(
            operation = {
                playerRepository.sellItem(item)
            },
            errorMessage = {
                "Failed to sell item ${item.name}."
            }
        )
    }

    fun deleteItem(item: InventoryItem) {
        executeOperation(
            operation = {
                playerRepository.deleteItem(item)
            },
            errorMessage = {
                "Failed to delete item ${item.name}."
            }
        )
    }

    private fun executeOperation(
        operation: suspend () -> Boolean,
        errorMessage: () -> String
    ) {
        viewModelScope.launch {
            _operationState.update { State.Loading() }
            try {
                if (operation()) {
                    _operationState.update { State.Loaded(Unit) }
                } else {
                    _operationState.update { State.Error(RuntimeException(errorMessage())) }
                }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (expected: Exception) {
                _operationState.update { State.Error(expected) }
            }
        }
    }
}
