package com.commondnd.data.storage

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.UnrecoverableKeyException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private const val ANDROID_KEYSTORE = "AndroidKeyStore"
private const val TRANSFORMATION = "AES/GCM/NoPadding"
private const val GCM_TAG_LENGTH = 128

// TODO: handle exception on security (e.g. pattern, finerprint) changes
fun getOrCreateKey(keyAlias: String): SecretKey {
    val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

    try {
        keyStore.getEntry(keyAlias, null)?.let {
            return (it as KeyStore.SecretKeyEntry).secretKey
        }
    } catch (_: UnrecoverableKeyException) {
        keyStore.deleteEntry(keyAlias)
    }

    val keyGenerator = KeyGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_AES,
        ANDROID_KEYSTORE
    )

    keyGenerator.init(
        KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()
    )

    return keyGenerator.generateKey()
}

fun encrypt(keyAlias: String, encrypted: String): ByteArray {
    val cipher = Cipher.getInstance(TRANSFORMATION)
    cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey(keyAlias))

    val iv = cipher.iv
    val encryptedBytes = cipher.doFinal(encrypted.toByteArray(Charsets.UTF_8))

    return iv + encryptedBytes
}

fun decrypt(keyAlias: String, encryptedData: ByteArray): String {
    val iv = encryptedData.copyOfRange(0, 12)
    val ciphertext = encryptedData.copyOfRange(12, encryptedData.size)

    return try {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(
            Cipher.DECRYPT_MODE,
            getOrCreateKey(keyAlias),
            GCMParameterSpec(GCM_TAG_LENGTH, iv)
        )
        cipher.doFinal(ciphertext).toString(Charsets.UTF_8)
    } catch (keyPermanentlyInvalidatedException: KeyPermanentlyInvalidatedException) {
        deleteKey(keyAlias)
        throw keyPermanentlyInvalidatedException
    } catch (invalidKeyException: InvalidKeyException) {
        deleteKey(keyAlias)
        throw invalidKeyException
    }
}

fun deleteKey(keyAlias: String) {
    val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
    keyStore.deleteEntry(keyAlias)
}
