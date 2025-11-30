package com.commondnd.data.user

import javax.inject.Inject

internal interface TokenStorage {

    fun get(): String?

    fun store(token: String)
}

internal class TokenStorageImpl @Inject constructor() : TokenStorage {

    private var mockStorage: String? = null

    override fun get(): String? {
        return mockStorage
    }

    override fun store(token: String) {
        mockStorage = token
    }
}
