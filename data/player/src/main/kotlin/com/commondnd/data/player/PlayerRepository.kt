package com.commondnd.data.player

import com.commondnd.data.core.Rarity
import com.commondnd.data.core.Synchronizable
import com.commondnd.data.user.UserRepository
import kotlinx.coroutines.CancellationException
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

    suspend fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): TokenConversionResult

    suspend fun doTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): Boolean
}

internal class PlayerRepositoryImpl @Inject constructor(
    private val playerRemoteSource: PlayerRemoteSource,
    private val playerRemoteOperations: PlayerRemoteOperations,
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

    override suspend fun calculateTokenConversion(
        from: Rarity,
        to: Rarity,
        value: Int
    ): TokenConversionResult {
        return playerRemoteOperations.calculateTokenConversion(
            from = from,
            to = to,
            value = value
        )
    }

    override suspend fun doTokenConversion(from: Rarity, to: Rarity, value: Int): Boolean {
        try {
            playerRemoteOperations.doTokenConversion(
                from = from,
                to = to,
                value = value
            )
            synchronize()
            return true
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (_: Exception) {
            return false
        }
    }

    override suspend fun synchronize(): Boolean {
        userRepository.getUser()?.id?.let { init(it) }
        return true
    }

    private suspend fun initAndGet(): Player {
        initIfNeeded()
        return ownPlayerData.value ?: throw IOException("Could not fetch player data.")
    }

    private suspend fun initIfNeeded() {
        if (ownPlayerData.value == null) {
            init(requireNotNull(userRepository.getUser()).id)
        }
    }

    private suspend fun init(playerId: String) {
        ownPlayerData.update { getPlayer(playerId) }
    }

    private suspend fun getPlayer(playerId: String): Player {
        var player: Player? = playerLocalSource.getPlayer(playerId)
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
