package com.commondnd.data.user

import javax.inject.Inject

internal interface AuthRemoteDataSource {
}

internal class AuthRemoteDataSourceImpl @Inject constructor() : AuthRemoteDataSource
