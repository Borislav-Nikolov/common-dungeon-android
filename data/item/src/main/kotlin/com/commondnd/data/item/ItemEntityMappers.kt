package com.commondnd.data.item

import com.commondnd.data.storage.InventoryItemEntity

fun InventoryItem.toEntity(playerId: String): InventoryItemEntity = InventoryItemEntity(
    playerId = playerId,
    name = name,
    rarity = rarity,
    rarityLevel = rarityLevel,
    banned = banned,
    official = official,
    index = index,
    quantity = quantity,
    sellable = sellable
)

fun InventoryItemEntity.toDomain(): InventoryItem = InventoryItem(
    name = name,
    rarity = rarity,
    rarityLevel = rarityLevel,
    banned = banned,
    official = official,
    index = index,
    quantity = quantity,
    sellable = sellable
)
