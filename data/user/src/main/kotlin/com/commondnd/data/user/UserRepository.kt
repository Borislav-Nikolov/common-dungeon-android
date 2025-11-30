package com.commondnd.data.user

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
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
    private val coroutineScope: CoroutineScope
) : UserRepository {

    private val mockCachedUser: MutableStateFlow<User?> = MutableStateFlow(null)

    init {

        coroutineScope.launch {
            tokenStorage.get()?.let {
                // TODO
                mockCachedUser.update { remoteSource.getUser().also { localSource.store(it) } }
            }
        }
    }

    override fun getUser(): User? {
        // TODO
        return mockCachedUser.value
    }

    override fun monitorUser(): Flow<User?> {
        // TODO
        return mockCachedUser
    }

    override suspend fun login(userAuthData: UserAuthData) {
        tokenStorage.store(remoteSource.getToken(userAuthData))
        localSource.store(remoteSource.getUser())
        mockCachedUser.update { localSource.get() }
    }

    override suspend fun logout() {
        tokenStorage.clear()
        localSource.clear()
        mockCachedUser.update { null }
    }
}
