package com.commondnd.data.player

import com.commondnd.data.core.Rarity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoTokenConversionRequestData(
    @SerialName("from_rarity")
    val fromRarity: Rarity,
    @SerialName("to_rarity")
    val toRarity: Rarity,
    @SerialName("value")
    val value: Int
)
