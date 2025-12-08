package com.commondnd.data.player

import com.commondnd.data.storage.PlayerDao
import javax.inject.Inject

interface PlayerLocalSource {
}

internal class PlayerLocalSourceImpl @Inject constructor(
    playerDao: PlayerDao
) : PlayerLocalSource {

}
