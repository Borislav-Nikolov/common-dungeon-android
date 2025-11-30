package com.commondnd.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase

// AppDatabase.kt
@Database(
    entities = [
        PlayerEntity::class,
        PlayerCharacterEntity::class,
        CharacterClassEntity::class,
        InventoryItemEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CommonDungeonDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun playerCharacterDao(): PlayerCharacterDao
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun inventoryItemDao(): InventoryItemDao
}
