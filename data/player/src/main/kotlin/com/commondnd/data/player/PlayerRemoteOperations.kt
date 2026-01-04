package com.commondnd.data.player

import com.commondnd.data.character.CharacterStatus
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.core.Rarity
import com.commondnd.data.item.InventoryItem
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

    suspend fun deleteItem(
        item: InventoryItem
    ): MessageResponse

    suspend fun sellItem(
        item: InventoryItem
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

    override suspend fun changeCharacterStatus(
        status: CharacterStatus,
        character: PlayerCharacter
    ): MessageResponse {
        return playerService.updateCharacterStatus(
            data = ChangeCharacterStatusRequestData(
                characterName = character.characterName,
                newStatus = status
            )
        )
    }

    override suspend fun deleteItem(item: InventoryItem): MessageResponse {
        return playerService.deleteItemFromInventory(
            data = InventoryItemRequestData(
                itemIndex = item.index
            )
        )
    }

    override suspend fun sellItem(item: InventoryItem): MessageResponse {
        return playerService.sellItemFromInventory(
            data = InventoryItemRequestData(
                itemIndex = item.index
            )
        )
    }
}
