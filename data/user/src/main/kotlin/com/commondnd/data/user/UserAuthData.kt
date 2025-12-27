package com.commondnd.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData(
    @SerialName("code")
    val code: String,
    @SerialName("code_verifier")
    val codeVerifier: String,
    @SerialName("redirect_uri")
    val redirectUri: String
)
