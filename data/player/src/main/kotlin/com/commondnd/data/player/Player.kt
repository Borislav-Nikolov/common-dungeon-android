package com.commondnd.data.player

import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.item.InventoryItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    @SerialName("player_id")
    val playerId: String,
    @SerialName("player_level")
    val playerLevel: Int,
    @SerialName("player_role")
    val playerRole: String,
    @SerialName("player_status")
    val playerStatus: String,
    @SerialName("name")
    val name: String,
    @SerialName("sessions_on_this_level")
    val sessionsOnThisLevel: Int,
    @SerialName("common_tokens")
    val commonTokens: Int,
    @SerialName("uncommon_tokens")
    val uncommonTokens: Int,
    @SerialName("rare_tokens")
    val rareTokens: Int,
    @SerialName("very_rare_tokens")
    val veryRareTokens: Int,
    @SerialName("legendary_tokens")
    val legendaryTokens: Int,
    @SerialName("characters")
    val characters: List<PlayerCharacter>,
    @SerialName("inventory")
    val inventory: List<InventoryItem>
)
