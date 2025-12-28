package com.commondnd.data.user

import android.util.Log
import com.commondnd.data.core.Synchronizable
import com.commondnd.data.storage.PlayerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

interface UserRepository {

    fun getUser(): User?
    fun monitorUser(): Flow<User?>
    suspend fun login(userAuthData: UserAuthData)
    suspend fun logout()
}

internal class UserRepositoryImpl @Inject constructor(
    private val remoteSource: AuthRemoteDataSource,
    private val localSource: UserLocalDataSource,
    private val tokenStorage: TokenStorage,
    private val playerDao: PlayerDao,
    coroutineScope: CoroutineScope
) : UserRepository, Synchronizable {

    private val cachedUser: MutableStateFlow<User?> = MutableStateFlow(null)

    init {

        coroutineScope.launch {
            Log.d("sadljaslfdjaslkdfj", "initiateUser: ${this@UserRepositoryImpl}")
            initiateUser() }
    }

    override fun getUser(): User? = cachedUser.value

    override fun monitorUser(): Flow<User?> = cachedUser

    override suspend fun login(userAuthData: UserAuthData) {
        Log.d("sadljaslfdjaslkdfj", "remoteSource.getToken(userAuthData)")
        tokenStorage.store(remoteSource.getToken(userAuthData))
        Log.d("sadljaslfdjaslkdfj", "remoteSource.getUser()")
        localSource.store(remoteSource.getUser())
        Log.d("sadljaslfdjaslkdfj", "cachedUser.update { localSource.get() }")
        cachedUser.update { localSource.get() }
    }

    override suspend fun logout() {
        remoteSource.logout()
        tokenStorage.clear()
        localSource.clear()
        playerDao.deleteAllPlayers()
        cachedUser.update { null }
    }

    override suspend fun synchronize(): Boolean {
        Log.d("sadljaslfdjaslkdfj", "synchronize")
        val token = tokenStorage.get()
        if (token == null) {
            localSource.clear()
            playerDao.deleteAllPlayers()
            return true
        }
        Log.d("sadljaslfdjaslkdfj", "synchronize.get user")
        val remoteUser = try {
            remoteSource.getUser()
        } catch (_: Exception) {
            Log.d("sadljaslfdjaslkdfj", "synchronize.logout")
            logout()
            return true
        }
        localSource.store(remoteUser)
        return true
    }

    private suspend fun initiateUser(): User? = tokenStorage.get()?.let {
        cachedUser.updateAndGet { getOrFetchUser() }
    }

    private suspend fun getOrFetchUser(): User? {
        return localSource.get() ?: try {
            remoteSource.getUser().also { localSource.store(it) }
        } catch (_: Exception) {
            Log.d("sadljaslfdjaslkdfj", "logging out")
            logout()
            return null
        }
    }
}
