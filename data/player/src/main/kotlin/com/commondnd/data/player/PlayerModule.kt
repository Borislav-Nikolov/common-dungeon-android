package com.commondnd.data.player

import com.commondnd.data.core.Synchronizable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PlayerModule {

    @Binds
    @Singleton
    abstract fun bindsPlayerRemoteSource(impl: PlayerRemoteSourceImpl): PlayerRemoteSource

    @Binds
    @Singleton
    abstract fun bindsPlayerLocalSource(impl: PlayerLocalSourceImpl): PlayerLocalSource

    @Binds
    @Singleton
    abstract fun bindsPlayerRepository(impl: PlayerRepositoryImpl): PlayerRepository

    companion object {

        @Provides
        @Singleton
        @IntoSet
        fun providePlayerRepositorySynchronizable(
            playerRepository: PlayerRepository
        ): Synchronizable = playerRepository as Synchronizable

        @Provides
        @Singleton
        fun providesUserService(
            retrofit: Retrofit
        ): PlayerService = retrofit.create(PlayerService::class.java)
    }
}
