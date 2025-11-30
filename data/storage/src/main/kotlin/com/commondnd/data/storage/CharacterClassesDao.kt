package com.commondnd.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CharacterClassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(characterClass: CharacterClassEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<CharacterClassEntity>)

    @Update
    suspend fun updateClass(characterClass: CharacterClassEntity)

    @Delete
    suspend fun deleteClass(characterClass: CharacterClassEntity)

    @Query("SELECT * FROM character_classes WHERE character_id = :characterId")
    suspend fun getClassesByCharacterId(characterId: Long): List<CharacterClassEntity>

    @Query("DELETE FROM character_classes WHERE character_id = :characterId")
    suspend fun deleteClassesByCharacterId(characterId: Long)
}
