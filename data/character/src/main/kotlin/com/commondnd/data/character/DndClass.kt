package com.commondnd.data.character

enum class DndClass {

    Artificer,
    Barbarian,
    Bard,
    Cleric,
    Druid,
    Fighter,
    Monk,
    Paladin,
    Ranger,
    Rogue,
    Sorcerer,
    Warlock,
    Wizard;

    companion object {

        fun fromString(value: String): DndClass? = when (value) {
            Artificer.name -> Artificer
            Barbarian.name -> Barbarian
            Bard.name -> Bard
            Cleric.name -> Cleric
            Druid.name -> Druid
            Fighter.name -> Fighter
            Monk.name -> Monk
            Paladin.name -> Paladin
            Ranger.name -> Ranger
            Rogue.name -> Rogue
            Sorcerer.name -> Sorcerer
            Warlock.name -> Warlock
            Wizard.name -> Wizard
            else -> null
        }
    }
}
