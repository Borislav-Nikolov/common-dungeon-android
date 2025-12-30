package com.commondnd.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.commondnd.data.character.DndClass

val DndClass.icon: Painter
    @Composable get() = painterResource(
        when (this) {
            DndClass.Artificer -> R.drawable.ic_dnd_class_artificer
            DndClass.Barbarian -> R.drawable.ic_dnd_class_barbarian
            DndClass.Bard -> R.drawable.ic_dnd_class_bard
            DndClass.Cleric -> R.drawable.ic_dnd_class_cleric
            DndClass.Druid -> R.drawable.ic_dnd_class_druid
            DndClass.Fighter -> R.drawable.ic_dnd_class_fighter
            DndClass.Monk -> R.drawable.ic_dnd_class_monk
            DndClass.Paladin -> R.drawable.ic_dnd_class_paladin
            DndClass.Ranger -> R.drawable.ic_dnd_class_ranger
            DndClass.Rogue -> R.drawable.ic_dnd_class_rogue
            DndClass.Sorcerer -> R.drawable.ic_dnd_class_sorcerer
            DndClass.Warlock -> R.drawable.ic_dnd_class_warlock
            DndClass.Wizard -> R.drawable.ic_dnd_class_wizard
        }
    )
