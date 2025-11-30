package com.commondnd.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAccessToken(
    @SerialName("access_token")
    val accessToken: String
)
