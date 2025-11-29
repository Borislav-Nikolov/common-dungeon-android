package com.commondnd.data.authorization

data class OAuthConfiguration(
    val authUrl: String,
    val clientId: String,
    val scope: String,
    val redirectUri: String,
    val codeVerifier: String
) {

    companion object {

        @Suppress("FunctionName")
        fun Discord(
            redirectUri: String,
            codeVerifier: String
        ) = OAuthConfiguration(
            authUrl = "https://discord.com/oauth2/authorize",
            clientId = "1071147578818838559",
            scope = "identify",
            redirectUri = redirectUri,
            codeVerifier = codeVerifier
        )
    }
}
