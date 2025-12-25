package com.commondnd.data.player

import com.commondnd.data.core.Synchronizable
import com.commondnd.data.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val playerLocalSource: PlayerLocalSource,
    private val coroutineScope: CoroutineScope,
    private val userRepository: UserRepository
) : PlayerRepository, Synchronizable {

    private val ownPlayerData = MutableStateFlow<Player?>(null)

    override fun monitorOwnPlayerData(): Flow<Player> {
        coroutineScope.launch { initIfNeeded() }
        return ownPlayerData.filterNotNull()
    }

    override suspend fun getOwnPlayerData(): Player {
        return initAndGet()
    }

    override suspend fun synchronize(): Boolean {
        init()
        return true
    }

    private suspend fun initAndGet(): Player {
        initIfNeeded()
        return ownPlayerData.value ?: throw IOException("Could not fetch player data.")
    }

    private suspend fun initIfNeeded() {
        if (ownPlayerData.value == null) {
            init()
        }
    }

    private suspend fun init() {
        ownPlayerData.update { getPlayer() }
    }

    private suspend fun getPlayer(): Player {
        val user = requireNotNull(userRepository.getUser())
        var player: Player? = playerLocalSource.getPlayer(user.id)
        if (player == null) {
            player = playerRemoteSource.getOwnPlayerData(
                includeInventory = true,
                includeCharacters = true
            )
            playerLocalSource.storePlayer(player)
        }
        return requireNotNull(player)
    }
}
