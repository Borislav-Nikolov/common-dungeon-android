package com.commondnd.data.user

import javax.inject.Inject

internal interface UserLocalDataSource {

    fun get(): User?

    fun store(user: User)

    fun clear()
}

internal class UserLocalDataSourceImpl @Inject constructor() : UserLocalDataSource {

    private var mockStorage: User? = null

    override fun get(): User? {
        return mockStorage
    }

    override fun store(user: User) {
        mockStorage = user
    }

    override fun clear() {
        mockStorage = null
    }
}
