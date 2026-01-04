package com.commondnd.data.player

import androidx.room.Transaction
import com.commondnd.data.character.toEntity
import com.commondnd.data.item.toEntity
import com.commondnd.data.storage.CharacterClassDao
import com.commondnd.data.storage.InventoryItemDao
import com.commondnd.data.storage.PlayerCharacterDao
import com.commondnd.data.storage.PlayerDao
import javax.inject.Inject

interface PlayerLocalSource {

    suspend fun getPlayer(playerId: String): Player?

    suspend fun storePlayer(player: Player)
}

internal class PlayerLocalSourceImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val characterDao: PlayerCharacterDao,
    private val characterClassDao: CharacterClassDao,
    private val itemDao: InventoryItemDao
) : PlayerLocalSource {

    override suspend fun getPlayer(playerId: String): Player? {
        return playerDao.getPlayerWithRelations(playerId)?.toDomain()
    }

    @Transaction
    override suspend fun storePlayer(player: Player) {
        playerDao.upsertPlayer(player.toEntity())
        player.inventory?.let { inventory ->
            itemDao.deleteItemsByPlayerId(player.playerId)
            val items = inventory.map { it.toEntity(player.playerId) }
            if (items.isNotEmpty()) {
                itemDao.upsertItems(items)
            }
        }
        player.characters?.let { characters ->
            characterDao.deleteCharactersByPlayerId(player.playerId)
            val characterEntities = characters.map { it.toEntity(player.playerId) }
            val characterIds = characterDao.upsertCharacters(characterEntities)
            val allClasses = characters.flatMapIndexed { index, character ->
                val characterId = characterIds[index]
                character.classes.map { it.toEntity(characterId) }
            }
            if (allClasses.isNotEmpty()) {
                characterClassDao.upsertClass(allClasses)
            }
        }
    }
}
