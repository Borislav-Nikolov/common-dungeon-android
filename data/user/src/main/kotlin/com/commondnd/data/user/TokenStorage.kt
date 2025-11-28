package com.commondnd.data.user

import javax.inject.Inject

internal interface TokenStorage {
}

internal class TokenStorageImpl @Inject constructor() : TokenStorage
