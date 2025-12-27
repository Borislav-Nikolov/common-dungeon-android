package com.commondnd.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<InventoryItemEntity>)

    @Upsert
    suspend fun upsertItem(item: InventoryItemEntity)

    @Upsert
    suspend fun upsertItems(items: List<InventoryItemEntity>)

    @Update
    suspend fun updateItem(item: InventoryItemEntity)

    @Delete
    suspend fun deleteItem(item: InventoryItemEntity)

    @Query("SELECT * FROM inventory_items WHERE player_id = :playerId")
    fun getItemsByPlayerId(playerId: String): Flow<List<InventoryItemEntity>>

    @Query("SELECT * FROM inventory_items WHERE player_id = :playerId AND name = :itemName")
    suspend fun getItemByPlayerIdAndName(playerId: String, itemName: String): InventoryItemEntity?

    @Query("DELETE FROM inventory_items WHERE player_id = :playerId")
    suspend fun deleteItemsByPlayerId(playerId: String)
}
