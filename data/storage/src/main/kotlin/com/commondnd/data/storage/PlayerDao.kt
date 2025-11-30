package com.commondnd.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE player_id = :playerId")
    suspend fun getPlayerById(playerId: String): PlayerEntity?

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Transaction
    @Query("SELECT * FROM players WHERE player_id = :playerId")
    suspend fun getPlayerWithRelations(playerId: String): PlayerWithRelations?

    @Transaction
    @Query("SELECT * FROM players")
    fun getAllPlayersWithRelations(): Flow<List<PlayerWithRelations>>

    @Query("DELETE FROM players")
    suspend fun deleteAllPlayers()
}
