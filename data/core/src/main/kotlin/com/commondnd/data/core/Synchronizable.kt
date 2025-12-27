package com.commondnd.data.core

interface Synchronizable {

    suspend fun synchronize(): Boolean
}
