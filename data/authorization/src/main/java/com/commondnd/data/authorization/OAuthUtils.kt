package com.commondnd.data.authorization

data class OAuthResult(
    val code: String,
    val codeVerifier: String,
    val redirectUri: String
)
