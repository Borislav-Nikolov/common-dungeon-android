package com.commondnd.data.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface UserRepository {

    fun getUser(): User?
    fun monitorUser(): Flow<User?>
    suspend fun login(userAuthData: UserAuthData)
    fun logout()
}

internal class UserRepositoryImpl @Inject constructor(
    private val remoteSource: AuthRemoteDataSource,
    private val localSource: UserLocalDataSource,
    private val tokenStorage: TokenStorage,
) : UserRepository {

    private val mockCachedUser: MutableStateFlow<User?> = MutableStateFlow(null)

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

    override fun logout() {
        // TODO
        mockCachedUser.update { null }
    }

}
