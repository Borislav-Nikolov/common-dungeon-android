package com.commondnd.data.user

import com.commondnd.data.storage.PlayerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
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
) : UserRepository {

    private val cachedUser: MutableStateFlow<User?> = MutableStateFlow(null)

    init {

        coroutineScope.launch { initiateUser() }
    }

    override fun getUser(): User? = cachedUser.value

    override fun monitorUser(): Flow<User?> = cachedUser

    override suspend fun login(userAuthData: UserAuthData) {
        tokenStorage.store(remoteSource.getToken(userAuthData))
        localSource.store(remoteSource.getUser())
        cachedUser.update { localSource.get() }
    }

    override suspend fun logout() {
        remoteSource.logout()
        tokenStorage.clear()
        localSource.clear()
        playerDao.deleteAllPlayers()
        cachedUser.update { null }
    }

    private suspend fun initiateUser(): User? = tokenStorage.get()?.let {
        cachedUser.updateAndGet { getOrFetchUser() }
    }

    private suspend fun getOrFetchUser(): User {
        return localSource.get() ?: remoteSource.getUser().also { localSource.store(it) }
    }
}
