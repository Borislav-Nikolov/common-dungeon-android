package com.commondnd.data.player

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        fun providesUserService(
            retrofit: Retrofit
        ): PlayerService = retrofit.create(PlayerService::class.java)
    }
}
