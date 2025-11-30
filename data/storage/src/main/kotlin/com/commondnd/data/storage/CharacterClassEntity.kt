package com.commondnd.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "character_classes",
    foreignKeys = [
        ForeignKey(
            entity = PlayerCharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["character_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("character_id")]
)
data class CharacterClassEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "character_id")
    val characterId: Long,
    @ColumnInfo(name = "class_name")
    val className: String,
    @ColumnInfo(name = "is_primary")
    val isPrimary: Boolean,
    @ColumnInfo(name = "level")
    val level: Int
)
