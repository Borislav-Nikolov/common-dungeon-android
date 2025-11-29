package com.commondnd.ui.login

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginModule {

    @Binds
    @Singleton
    abstract fun bindLoginController(
        impl: LoginControllerImpl
    ): LoginController
}
