package com.commondnd.data.player

import javax.inject.Inject

interface PlayerRemoteSource {

    suspend fun getOwnPlayerData(
        includeInventory: Boolean = false,
        includeCharacters: Boolean = false
    ): Player
}

internal class PlayerRemoteSourceImpl @Inject constructor(
    private val playerService: PlayerService
) : PlayerRemoteSource {

    override suspend fun getOwnPlayerData(
        includeInventory: Boolean,
        includeCharacters: Boolean
    ): Player {
        return playerService.getOwnPlayerData(
            includeInventory = includeInventory,
            includeCharacters = includeCharacters
        )
    }
}