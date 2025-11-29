package com.commondnd.data.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface UserRepository {

    fun getUser(): User?
    fun monitorUser(): Flow<User?>
    suspend fun login(code: String, codeVerifier: String)
    fun logout()
}

internal class UserRepositoryImpl @Inject constructor(
    private val remoteSource: AuthRemoteDataSource,
    private val localSource: UserLocalDataSource,
    private val tokenStorage: TokenStorage
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

    override suspend fun login(code: String, codeVerifier: String) {
        // TODO
        mockCachedUser.update { User("1234", "MockUser", "mockuser1234", "123asdASD") }
    }

    override fun logout() {
        // TODO
        mockCachedUser.update { null }
    }

}
