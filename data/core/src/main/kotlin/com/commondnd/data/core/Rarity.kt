package com.commondnd.data.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = RaritySerializer::class)
enum class Rarity(
    val value: String
) {

    Common("Common"),
    Uncommon("Uncommon"),
    Rare("Rare"),
    VeryRare("Very Rare"),
    Legendary("Legendary");

    companion object {

        fun fromString(value: String): Rarity =
            Rarity.entries.first { it.name.equals(value, ignoreCase = true) }
    }
}


object RaritySerializer : KSerializer<Rarity> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Rarity", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Rarity) {
        encoder.encodeString(value.name)
    }

    override fun deserialize(decoder: Decoder): Rarity =
        Rarity.fromString(decoder.decodeString())
}