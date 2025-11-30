package com.commondnd.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal const val USER_STORE = "secure_tokens"
internal val Context.userDataStore by preferencesDataStore(name = USER_STORE)

internal interface UserLocalDataSource {

    fun monitor(): Flow<User?>

    suspend fun get(): User?

    suspend fun store(user: User)

    suspend fun clear()
}

internal class UserLocalDataSourceImpl @Inject constructor(
    @param:UserStore private val userDataStore: DataStore<Preferences>,
    private val json: Json
) : UserLocalDataSource {

    private companion object {

        val USER_KEY = stringPreferencesKey("user_data")
    }

    override fun monitor(): Flow<User?> {
        return userDataStore.data
            .map { prefs -> prefs[USER_KEY]?.let { json.decodeFromString(it) } }
    }

    override suspend fun get(): User? {
        return monitor().first()
    }

    override suspend fun store(user: User) {
        userDataStore.edit { prefs -> prefs[USER_KEY] = json.encodeToString(user) }
    }

    override suspend fun clear() {
        userDataStore.edit { prefs -> prefs.remove(USER_KEY) }
    }
}
