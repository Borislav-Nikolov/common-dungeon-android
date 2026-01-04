package com.commondnd.data.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryItemRequestData(
    @SerialName("item_index")
    val itemIndex: Int
)
