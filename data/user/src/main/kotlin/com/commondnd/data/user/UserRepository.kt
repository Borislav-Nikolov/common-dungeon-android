package com.commondnd.data.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {

    fun getUser(): User
    fun monitorUser(): Flow<User>
    fun login(token: String)
    fun logout()
}

internal class UserRepositoryImpl @Inject constructor(
    private val remoteSource: AuthRemoteDataSource,
    private val localSource: UserLocalDataSource,
    private val tokenStorage: TokenStorage
) : UserRepository {
    override fun getUser(): User {
        // TODO
        return User("-1", "asd", "ASD", "123asdASD")
    }

    override fun monitorUser(): Flow<User> {
        // TODO
        return flow {
            emit(User("-1", "asd", "ASD", "123asdASD"))
        }
    }

    override fun login(token: String) {
        // TODO
    }

    override fun logout() {
        // TODO
    }

}
