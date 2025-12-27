package com.commondnd.data.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(
        @ApplicationContext context: Context
    ): CommonDungeonDatabase = Room.databaseBuilder(
        context.applicationContext,
        CommonDungeonDatabase::class.java,
        "commondnd_database"
    ).build()

    @Provides
    @Singleton
    fun providesPlayerDao(
        database: CommonDungeonDatabase
    ) = database.playerDao()

    @Provides
    @Singleton
    fun providesPlayerCharacterDao(
        database: CommonDungeonDatabase
    ) = database.playerCharacterDao()

    @Provides
    @Singleton
    fun providesCharacterClassDao(
        database: CommonDungeonDatabase
    ) = database.characterClassDao()

    @Provides
    @Singleton
    fun providesInventoryItemDao(
        database: CommonDungeonDatabase
    ) = database.inventoryItemDao()
}
