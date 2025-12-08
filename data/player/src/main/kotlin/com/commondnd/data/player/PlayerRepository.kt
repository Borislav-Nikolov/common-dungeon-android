package com.commondnd.data.player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

interface PlayerRepository {

    fun monitorOwnPlayerData(): Flow<Player>

    suspend fun getOwnPlayerData(): Player
}

internal class PlayerRepositoryImpl @Inject constructor(
    private val playerRemoteSource: PlayerRemoteSource,
    // TODO: use
    private val playerLocalSource: PlayerLocalSource,
    private val coroutineScope: CoroutineScope
) : PlayerRepository {

    private val ownPlayerData = MutableStateFlow<Player?>(null)

    override fun monitorOwnPlayerData(): Flow<Player> {
        coroutineScope.launch { initIfNeeded() }
        return ownPlayerData.filterNotNull()
    }

    override suspend fun getOwnPlayerData(): Player {
        return initAndGet()
    }

    private suspend fun initAndGet(): Player {
        initIfNeeded()
        return ownPlayerData.value ?: throw IOException("Could not fetch player data.")
    }

    private suspend fun initIfNeeded() {
        if (ownPlayerData.value == null) {
            ownPlayerData.update {
                playerRemoteSource.getOwnPlayerData(
                    includeInventory = true,
                    includeCharacters = true
                )
            }
        }
    }
}
