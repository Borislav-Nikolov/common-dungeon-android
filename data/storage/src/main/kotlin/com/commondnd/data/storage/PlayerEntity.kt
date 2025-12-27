package com.commondnd.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "player_id")
    val playerId: String,
    @ColumnInfo(name = "player_level")
    val playerLevel: Int,
    @ColumnInfo(name = "player_role")
    val playerRole: String,
    @ColumnInfo(name = "player_status")
    val playerStatus: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "sessions_on_this_level")
    val sessionsOnThisLevel: Int,
    @ColumnInfo(name = "common_tokens")
    val commonTokens: Int,
    @ColumnInfo(name = "uncommon_tokens")
    val uncommonTokens: Int,
    @ColumnInfo(name = "rare_tokens")
    val rareTokens: Int,
    @ColumnInfo(name = "very_rare_tokens")
    val veryRareTokens: Int,
    @ColumnInfo(name = "legendary_tokens")
    val legendaryTokens: Int
)
