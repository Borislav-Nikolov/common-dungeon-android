package com.commondnd.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inventory_items",
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
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "player_id")
    val playerId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rarity")
    val rarity: String,
    @ColumnInfo(name = "rarity_level")
    val rarityLevel: String,
    @ColumnInfo(name = "banned")
    val banned: Boolean,
    @ColumnInfo(name = "official")
    val official: Boolean,
    @ColumnInfo(name = "item_index")
    val index: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int
)
