package com.commondnd.data.authorization

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

// RFC 7636 allowed characters: A-Z, a-z, 0-9, -, ., _, ~
// Using alphanumeric only for maximum compatibility
private const val ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

private const val DEFAULT_LENGTH = 64
private const val MIN_LENGTH = 43
private const val MAX_LENGTH = 128

/**
 * Generates a cryptographically secure code verifier.
 *
 * Uses alphanumeric characters only for maximum provider compatibility.
 * Compliant with RFC 7636.
 *
 * @param length Length of the verifier (43-128, default 64)
 * @return A random code verifier string
 */
fun generateCodeVerifier(length: Int = DEFAULT_LENGTH): String {
    require(length in MIN_LENGTH..MAX_LENGTH) {
        "Code verifier length must be between $MIN_LENGTH and $MAX_LENGTH"
    }

    val secureRandom = SecureRandom()
    val charPool = ALLOWED_CHARS.toCharArray()

    return buildString(length) {
        repeat(length) {
            append(charPool[secureRandom.nextInt(charPool.size)])
        }
    }
}

/**
 * Generates a code challenge from a code verifier using SHA-256.
 *
 * The challenge is Base64 URL-encoded without padding, as required by RFC 7636.
 *
 * @param codeVerifier The code verifier to transform
 * @return The code challenge string
 */
fun generateCodeChallenge(codeVerifier: String): String {
    val bytes = codeVerifier.toByteArray(Charsets.US_ASCII)
    val digest = MessageDigest.getInstance("SHA-256").digest(bytes)

    return base64UrlEncode(digest)
}

private fun base64UrlEncode(bytes: ByteArray): String {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(bytes)
}
