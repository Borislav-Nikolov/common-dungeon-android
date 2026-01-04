package com.commondnd.data.player

import com.commondnd.data.character.CharacterStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeCharacterStatusRequestData(
    @SerialName("character_name")
    val characterName: String,
    @SerialName("new_status")
    val newStatus: CharacterStatus
)
