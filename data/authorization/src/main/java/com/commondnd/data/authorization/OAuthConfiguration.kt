package com.commondnd.data.authorization

data class OAuthConfiguration(
    val clientId: String,
    val scope: String,
    val redirectUri: String
) {

    val responseType = "code"

    companion object {

        @Suppress("FunctionName")
        fun Discord(
            redirectUri: String
        ) = OAuthConfiguration(
            clientId = "1071147578818838559",
            scope = "identify",
            redirectUri = redirectUri
        )
    }
}
