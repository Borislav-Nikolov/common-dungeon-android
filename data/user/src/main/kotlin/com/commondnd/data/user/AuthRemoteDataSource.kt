package com.commondnd.data.user

import javax.inject.Inject

internal interface AuthRemoteDataSource {

    suspend fun getToken(
        userAuthData: UserAuthData
    ): String

    suspend fun getUser(): User
}

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
) : AuthRemoteDataSource {

    override suspend fun getToken(userAuthData: UserAuthData): String {
        return userService.authorizeUser(userAuthData).accessToken
    }

    override suspend fun getUser(): User {
        return userService.getUser()
    }
}
