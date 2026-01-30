package com.commondnd.data.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = RaritySerializer::class)
enum class Rarity {

    Common,
    Uncommon,
    Rare,
    VeryRare,
    Legendary;

    fun getAllLesser(): List<Rarity> = when (this) {
        Common -> emptyList()
        Uncommon -> listOf(Common)
        Rare -> listOf(Common, Uncommon)
        VeryRare -> listOf(Common, Uncommon, Rare)
        Legendary -> listOf(Common, Uncommon, Rare, VeryRare)
    }

    fun getAllGreater(): List<Rarity> = when (this) {
        Common -> listOf(Uncommon, Rare, VeryRare, Legendary)
        Uncommon -> listOf(Rare, VeryRare, Legendary)
        Rare -> listOf(VeryRare, Legendary)
        VeryRare -> listOf(Legendary)
        Legendary -> emptyList()
    }

    companion object {

        fun fromString(value: String): Rarity =
            if (value.equals("very rare", ignoreCase = true)) {
                VeryRare
            } else {
                Rarity.entries.first { it.name.equals(value, ignoreCase = true) }
            }
    }
}


object RaritySerializer : KSerializer<Rarity> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Rarity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Rarity) {
        encoder.encodeString(
            when (value) {
                Rarity.Common -> "Common"
                Rarity.Uncommon -> "Uncommon"
                Rarity.Rare -> "Rare"
                Rarity.VeryRare -> "VeryRare"
                Rarity.Legendary -> "Legendary"
            }
        )
    }

    override fun deserialize(decoder: Decoder): Rarity =
        Rarity.fromString(decoder.decodeString())
}