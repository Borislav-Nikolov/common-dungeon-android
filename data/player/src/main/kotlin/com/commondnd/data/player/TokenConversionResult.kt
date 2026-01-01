package com.commondnd.data.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenConversionResult(
    @SerialName("subtracter")
    val subtracted: Int,
    @SerialName("added")
    val added: Int
)
