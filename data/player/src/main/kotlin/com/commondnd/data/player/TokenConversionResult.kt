package com.commondnd.data.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenConversionResult(
    @SerialName("subtracted")
    val subtracted: Int,
    @SerialName("added")
    val added: Int
)
