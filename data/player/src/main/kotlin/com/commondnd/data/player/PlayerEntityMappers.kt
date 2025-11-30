package com.commondnd.data.player

import com.commondnd.data.character.toDomain
import com.commondnd.data.item.InventoryItem
import com.commondnd.data.item.toDomain
import com.commondnd.data.storage.InventoryItemEntity
import com.commondnd.data.storage.PlayerEntity
import com.commondnd.data.storage.PlayerWithRelations

fun Player.toEntity(): PlayerEntity = PlayerEntity(
    playerId = playerId,
    playerLevel = playerLevel,
    playerRole = playerRole,
    playerStatus = playerStatus,
    name = name,
    sessionsOnThisLevel = sessionsOnThisLevel,
    commonTokens = commonTokens,
    uncommonTokens = uncommonTokens,
    rareTokens = rareTokens,
    veryRareTokens = veryRareTokens,
    legendaryTokens = legendaryTokens
)

fun PlayerWithRelations.toDomain(): Player = Player(
    playerId = player.playerId,
    playerLevel = player.playerLevel,
    playerRole = player.playerRole,
    playerStatus = player.playerStatus,
    name = player.name,
    sessionsOnThisLevel = player.sessionsOnThisLevel,
    commonTokens = player.commonTokens,
    uncommonTokens = player.uncommonTokens,
    rareTokens = player.rareTokens,
    veryRareTokens = player.veryRareTokens,
    legendaryTokens = player.legendaryTokens,
    characters = characters.map { it.toDomain() },
    inventory = inventory.map { it.toDomain() }
)

fun List<PlayerWithRelations>.toDomainList(): List<Player> = map { it.toDomain() }
fun List<InventoryItemEntity>.toInventoryDomainList(): List<InventoryItem> = map { it.toDomain() }
