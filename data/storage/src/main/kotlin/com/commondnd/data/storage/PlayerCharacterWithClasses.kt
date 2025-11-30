package com.commondnd.data.storage

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerCharacterWithClasses(
    @Embedded
    val character: PlayerCharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "character_id"
    )
    val classes: List<CharacterClassEntity>
)
