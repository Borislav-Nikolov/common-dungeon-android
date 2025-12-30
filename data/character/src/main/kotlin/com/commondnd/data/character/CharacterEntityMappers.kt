package com.commondnd.data.character

import com.commondnd.data.storage.CharacterClassEntity
import com.commondnd.data.storage.PlayerCharacterEntity
import com.commondnd.data.storage.PlayerCharacterWithClasses
import kotlinx.serialization.serializer

fun PlayerCharacter.toEntity(playerId: String): PlayerCharacterEntity = PlayerCharacterEntity(
    playerId = playerId,
    characterName = characterName,
    characterLevel = characterLevel,
    sessionsOnThisLevel = sessionsOnThisLevel,
    maxLevel = maxLevel,
    lastDm = lastDm,
    status = status.name
)

fun CharacterClass.toEntity(characterId: Long): CharacterClassEntity = CharacterClassEntity(
    characterId = characterId,
    className = className,
    isPrimary = isPrimary,
    level = level
)

fun PlayerCharacterWithClasses.toDomain(): PlayerCharacter = PlayerCharacter(
    characterName = character.characterName,
    characterLevel = character.characterLevel,
    sessionsOnThisLevel = character.sessionsOnThisLevel,
    maxLevel = character.maxLevel,
    lastDm = character.lastDm,
    status = CharacterStatus.fromString(character.status),
    classes = classes.map { it.toDomain() }
)

fun CharacterClassEntity.toDomain(): CharacterClass = CharacterClass(
    className = className,
    isPrimary = isPrimary,
    level = level
)
