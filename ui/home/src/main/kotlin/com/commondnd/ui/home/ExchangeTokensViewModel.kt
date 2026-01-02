package com.commondnd.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.commondnd.data.core.Rarity
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.data.player.PlayerRepository
import com.commondnd.data.player.TokenConversionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
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
class ExchangeTokensViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
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

    private val _calculationResult = MutableStateFlow<State<TokenConversionResult>>(State.None())
    val calculationResult: StateFlow<State<TokenConversionResult>> = _calculationResult

    private val _conversionResult = MutableStateFlow<State<Unit>>(State.None())
    val conversionResult: StateFlow<State<Unit>> = _conversionResult

    private var calculationJob: Job? = null

    fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ) {
        calculationJob?.cancel()
        calculationJob = viewModelScope.launch {
            _calculationResult.update { State.Loading() }
            _calculationResult.update {
                try {
                    State.Loaded(
                        playerRepository.calculateTokenConversion(
                            from = from,
                            to = to,
                            value = value
                        )
                    )
                } catch (cancellation: CancellationException) {
                    throw cancellation
                } catch (exception: Exception) {
                    State.Error(exception)
                }
            }
        }
    }

    fun doTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ) {
        viewModelScope.launch {
            _conversionResult.update { State.Loading() }
            _conversionResult.update {
                try {
                    if (playerRepository.doTokenConversion(
                        from = from,
                        to = to,
                        value = value
                    )) {
                        State.Loaded(Unit)
                    } else {
                        State.Error(RuntimeException("Failed to complete token conversion."))
                    }
                } catch (cancellation: CancellationException) {
                    throw cancellation
                } catch (exception: Exception) {
                    State.Error(exception)
                }
            }
        }
    }
}
