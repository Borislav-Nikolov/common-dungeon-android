package com.commondnd.data.storage

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerWithRelations(
    @Embedded
    val player: PlayerEntity,
    @Relation(
        parentColumn = "player_id",
        entityColumn = "player_id"
    )
    val inventory: List<InventoryItemEntity>,
    @Relation(
        parentColumn = "player_id",
        entityColumn = "player_id",
        entity = PlayerCharacterEntity::class
    )
    val characters: List<PlayerCharacterWithClasses>
)
