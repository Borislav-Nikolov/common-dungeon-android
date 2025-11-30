package com.commondnd.data.user

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.commondnd.data.storage.decrypt
import com.commondnd.data.storage.encrypt
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal const val TOKENS_STORE = "secure_tokens"
internal val Context.tokenDataStore by preferencesDataStore(name = TOKENS_STORE)

internal interface TokenStorage {

    suspend fun get(): String?

    suspend fun store(token: String)

    suspend fun clear()
}

internal class TokenStorageImpl @Inject constructor(
    @param:AuthToken private val tokenDataStore: DataStore<Preferences>
) : TokenStorage {

    private companion object {
        val TOKEN_KEY = stringPreferencesKey("encrypted_token")
        val TOKEN_KEY_ALIAS = "secured_user_token"
    }


    override suspend fun get(): String? {
        val encoded = tokenDataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .first() ?: return null

        return try {
            val encrypted = Base64.decode(encoded, Base64.DEFAULT)
            decrypt(TOKEN_KEY_ALIAS, encrypted)
        } catch (_: Exception) {
            clear()
            null
        }
    }

    override suspend fun store(token: String) {
        val encrypted = encrypt(TOKEN_KEY_ALIAS, token)
        val encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
        tokenDataStore.edit { prefs ->
            prefs[TOKEN_KEY] = encoded
        }
    }

    override suspend fun clear() {
        tokenDataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}
