package com.commondnd.data.user

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import retrofit2.Retrofit
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
        fun providesUserAuthTokenInterceptor(
            tokenStorage: TokenStorage
        ): Interceptor = UserAuthTokenInterceptor(tokenProvider = tokenStorage::get)

        @Provides
        @Singleton
        fun providesUserService(
            retrofit: Retrofit
        ): UserService = retrofit.create(UserService::class.java)
    }
}
