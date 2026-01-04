package com.commondnd.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.commondnd.data.core.Synchronizable
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindTokenStorage(
        tokenStorageImpl: TokenStorageImpl
    ): TokenStorage

    companion object {

        @Provides
        @Singleton
        @IntoSet
        fun provideUserRepositorySynchronizable(
            userRepository: UserRepository
        ): Synchronizable = userRepository as Synchronizable

        @Provides
        @Singleton
        @IntoSet
        fun providesUserAuthTokenInterceptor(
            tokenStorage: TokenStorage
        ): Interceptor = UserAuthTokenInterceptor(
            tokenProvider = { runBlocking {  tokenStorage.get() } }
        )

        @Provides
        @Singleton
        @IntoSet
        fun providesUnauthorizedResponseInterceptor(
            userRepository: Provider<UserRepository>
        ): Interceptor = UnauthorizedResponseInterceptor(
            onUnauthorizedResponse = { runBlocking {  userRepository.get().logout() } }
        )

        @Provides
        @Singleton
        fun providesUserService(
            retrofit: Retrofit
        ): UserService = retrofit.create(UserService::class.java)

        @Provides
        @Singleton
        @AuthToken
        fun providesAuthTokenDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.tokenDataStore

        @Provides
        @Singleton
        @UserStore
        fun providesUserDataStorePreferences(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.userDataStore
    }
}
