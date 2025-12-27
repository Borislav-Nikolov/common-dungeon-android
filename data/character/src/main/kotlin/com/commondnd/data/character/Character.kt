package com.commondnd.data.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterClass(
    @SerialName("class_name")
    val className: String,
    @SerialName("is_primary")
    val isPrimary: Boolean,
    @SerialName("level")
    val level: Int
)

@Serializable
data class PlayerCharacter(
    @SerialName("character_name")
    val characterName: String,
    @SerialName("character_level")
    val characterLevel: Int,
    @SerialName("sessions_on_this_level")
    val sessionsOnThisLevel: Int,
    @SerialName("last_dm")
    val lastDm: String,
    @SerialName("status")
    val status: String,
    @SerialName("classes")
    val classes: List<CharacterClass>
)
