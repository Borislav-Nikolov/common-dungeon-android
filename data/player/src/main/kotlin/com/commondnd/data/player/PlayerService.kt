package com.commondnd.data.player

import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerService {

    @GET("get_own_player_data")
    suspend fun getOwnPlayerData(
        @Query("include_inventory") includeInventory: Boolean = false,
        @Query("include_characters") includeCharacters: Boolean = false
    ): Player
}
