package com.commondnd.data.user

import android.util.Log
import com.commondnd.data.core.Synchronizable
import com.commondnd.data.storage.PlayerDao
import kotlinx.coroutines.CancellationException
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
    suspend fun getAsync(): User?
    fun monitorUser(): Flow<User?>
    suspend fun login(userAuthData: UserAuthData)
    suspend fun logout(): Boolean
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

        coroutineScope.launch { initiateUser() }
    }

    override fun getUser(): User? = cachedUser.value

    override suspend fun getAsync(): User? = getOrFetchUser()

    override fun monitorUser(): Flow<User?> = cachedUser

    override suspend fun login(userAuthData: UserAuthData) {
        tokenStorage.store(remoteSource.getToken(userAuthData))
        localSource.store(remoteSource.getUser())
        cachedUser.update { localSource.get() }
    }

    override suspend fun logout(): Boolean {
        try {
            remoteSource.logout()
            tokenStorage.clear()
            localSource.clear()
            playerDao.deleteAllPlayers()
            cachedUser.update { null }
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (_: Exception) {
            return false
        }
        return true
    }

    override suspend fun synchronize(): Boolean {
        val token = tokenStorage.get()
        if (token == null) {
            localSource.clear()
            playerDao.deleteAllPlayers()
            return true
        }
        val remoteUser = try {
            remoteSource.getUser()
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (_: Exception) {
            return logout()
        }
        localSource.store(remoteUser)
        return true
    }

    private suspend fun initiateUser(): User? =
        cachedUser.updateAndGet { getOrFetchUser() }

    private suspend fun getOrFetchUser(): User? {
        return tokenStorage.get()?.let {
            localSource.get() ?: try {
                remoteSource.getUser().also { localSource.store(it) }
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                logout()
                return null
            }
        }
    }
}
