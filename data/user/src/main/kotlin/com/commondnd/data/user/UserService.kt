package com.commondnd.data.user

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("auth/code")
    suspend fun authorizeUser(
        @Body userAuthData: UserAuthData
    ): UserAccessToken

    @GET("api/user")
    suspend fun getUser(): User
}
