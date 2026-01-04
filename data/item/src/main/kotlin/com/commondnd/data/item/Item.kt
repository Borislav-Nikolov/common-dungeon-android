package com.commondnd.data.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Item {
    val name: String
    val rarity: String
    val rarityLevel: String
    val banned: Boolean
    val official: Boolean
}

@Serializable
data class BaseItem(
    @SerialName("name")
    override val name: String,
    @SerialName("rarity")
    override val rarity: String,
    @SerialName("rarity_level")
    override val rarityLevel: String,
    @SerialName("banned")
    override val banned: Boolean,
    @SerialName("official")
    override val official: Boolean
) : Item

@Serializable
data class InventoryItem(
    @SerialName("name")
    override val name: String,
    @SerialName("rarity")
    override val rarity: String,
    @SerialName("rarity_level")
    override val rarityLevel: String,
    @SerialName("banned")
    override val banned: Boolean,
    @SerialName("official")
    override val official: Boolean,
    @SerialName("index")
    val index: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("sellable")
    val sellable: Boolean
) : Item

@Serializable
data class ShopItem(
    @SerialName("name")
    override val name: String,
    @SerialName("rarity")
    override val rarity: String,
    @SerialName("rarity_level")
    override val rarityLevel: String,
    @SerialName("banned")
    override val banned: Boolean,
    @SerialName("official")
    override val official: Boolean,
    @SerialName("index")
    val index: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("sold")
    val sold: Boolean
) : Item
