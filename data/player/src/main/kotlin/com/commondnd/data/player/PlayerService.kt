package com.commondnd.data.player

import com.commondnd.data.networking.MessageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlayerService {

    @GET("get_own_player_data")
    suspend fun getOwnPlayerData(
        @Query("include_inventory") includeInventory: Boolean = false,
        @Query("include_characters") includeCharacters: Boolean = false
    ): Player

    @GET("calculate_token_conversion")
    suspend fun calculateTokenConversion(
        @Query("from_rarity") fromRarity: String,
        @Query("to_rarity") toRarity: String,
        @Query("value") value: Int
    ): TokenConversionResult

    @POST("do_token_conversion")
    suspend fun doTokenConversion(
        @Body data: DoTokenConversionRequestData
    ): MessageResponse

    @POST("update_character_status")
    suspend fun updateCharacterStatus(
        @Body data: ChangeCharacterStatusRequestData
    ): MessageResponse

    @POST("sell_item_from_inventory")
    suspend fun sellItemFromInventory(
        @Body data: InventoryItemRequestData
    ): MessageResponse

    @POST("delete_item_from_inventory")
    suspend fun deleteItemFromInventory(
        @Body data: InventoryItemRequestData
    ): MessageResponse
}
