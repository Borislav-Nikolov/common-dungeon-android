package com.commondnd.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "player_characters",
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["player_id"],
            childColumns = ["player_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("player_id")]
)
data class PlayerCharacterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "player_id")
    val playerId: String,
    @ColumnInfo(name = "character_name")
    val characterName: String,
    @ColumnInfo(name = "character_level")
    val characterLevel: Int,
    @ColumnInfo(name = "sessions_on_this_level")
    val sessionsOnThisLevel: Int,
    @ColumnInfo(name = "max_level")
    val maxLevel: Int,
    @ColumnInfo(name = "last_dm")
    val lastDm: String,
    @ColumnInfo(name = "status")
    val status: String
)
