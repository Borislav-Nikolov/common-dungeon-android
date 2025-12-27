package com.commondnd.data.authorization

import android.annotation.SuppressLint
import android.net.Uri

@SuppressLint("UseKtx")
fun buildAuthorizationUrl(
    configuration: OAuthConfiguration
): Uri {

    val codeChallenge = generateCodeChallenge(configuration.codeVerifier)
    return Uri.parse(configuration.authUrl).buildUpon()
        .appendQueryParameter("client_id", configuration.clientId)
        .appendQueryParameter("response_type", "code")
        .appendQueryParameter("redirect_uri", configuration.redirectUri)
        .appendQueryParameter("scope", configuration.scope)
        .appendQueryParameter("code_challenge", codeChallenge)
        .appendQueryParameter("code_challenge_method", "S256")
        .build()
}

fun parseAuthResult(
    resultUri: Uri?,
    verifier: String,
    redirect: String
): OAuthResult? {
    if (resultUri == null) return null

    val code = resultUri.getQueryParameter("code") ?: return null

    return OAuthResult(
        code = code,
        codeVerifier = verifier,
        redirectUri = redirect
    )
}
