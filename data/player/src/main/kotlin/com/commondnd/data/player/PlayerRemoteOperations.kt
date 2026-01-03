package com.commondnd.data.player

import com.commondnd.data.character.CharacterStatus
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.core.Rarity
import com.commondnd.data.networking.MessageResponse
import javax.inject.Inject

interface PlayerRemoteOperations {

    suspend fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): TokenConversionResult

    suspend fun doTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): MessageResponse

    suspend fun changeCharacterStatus(
        status: CharacterStatus,
        character: PlayerCharacter
    ): MessageResponse
}

internal class PlayerRemoteOperationsImpl @Inject constructor(
    private val playerService: PlayerService
) : PlayerRemoteOperations {

    override suspend fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): TokenConversionResult {
        return playerService.calculateTokenConversion(
            fromRarity = from.name,
            toRarity = to.name,
            value = value
        )
    }

    override suspend fun doTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): MessageResponse {
        return playerService.doTokenConversion(
            data = DoTokenConversionRequestData(
                fromRarity = from,
                toRarity = to,
                value = value
            )
        )
    }
}
