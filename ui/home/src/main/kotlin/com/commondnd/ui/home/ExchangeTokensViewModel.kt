package com.commondnd.ui.home

import androidx.lifecycle.ViewModel
import com.commondnd.data.core.Rarity
import com.commondnd.data.player.PlayerRepository
import com.commondnd.data.player.TokenConversionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeTokensViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    suspend fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): TokenConversionResult = playerRepository.calculateTokenConversion(
        from = from,
        to = to,
        value = value
    )

    suspend fun doTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): Boolean = playerRepository.doTokenConversion(
        from = from,
        to = to,
        value = value
    )
}
