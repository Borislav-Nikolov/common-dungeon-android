package com.commondnd.data.user

import javax.inject.Inject

internal interface UserLocalDataSource {
}

internal class UserLocalDataSourceImpl @Inject constructor() : UserLocalDataSource
