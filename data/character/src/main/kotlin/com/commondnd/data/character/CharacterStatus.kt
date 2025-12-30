package com.commondnd.data.character

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = CharacterStatusSerializer::class)
enum class CharacterStatus {
    Active, Inactive, Dead;

    companion object {

        fun fromString(value: String): CharacterStatus =
            CharacterStatus.entries.first { it.name.equals(value, ignoreCase = true) }
    }
}

object CharacterStatusSerializer : KSerializer<CharacterStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CharacterStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CharacterStatus) {
        encoder.encodeString(value.name)
    }

    override fun deserialize(decoder: Decoder): CharacterStatus =
        CharacterStatus.fromString(decoder.decodeString())
}
