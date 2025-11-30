package com.commondnd.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PlayerCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: PlayerCharacterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<PlayerCharacterEntity>): List<Long>

    @Update
    suspend fun updateCharacter(character: PlayerCharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: PlayerCharacterEntity)

    @Query("SELECT * FROM player_characters WHERE player_id = :playerId")
    suspend fun getCharactersByPlayerId(playerId: String): List<PlayerCharacterEntity>

    @Transaction
    @Query("SELECT * FROM player_characters WHERE id = :characterId")
    suspend fun getCharacterWithClasses(characterId: Long): PlayerCharacterWithClasses?

    @Transaction
    @Query("SELECT * FROM player_characters WHERE player_id = :playerId")
    suspend fun getCharactersWithClassesByPlayerId(playerId: String): List<PlayerCharacterWithClasses>

    @Query("DELETE FROM player_characters WHERE player_id = :playerId")
    suspend fun deleteCharactersByPlayerId(playerId: String)
}
