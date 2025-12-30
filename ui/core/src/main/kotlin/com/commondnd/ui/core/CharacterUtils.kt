package com.commondnd.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.ui.material3.legendaryTokenColor
import com.commondnd.ui.material3.rareTokenColor
import com.commondnd.ui.material3.uncommonTokenColor
import com.commondnd.ui.material3.veryRareTokenColor

val PlayerCharacter.tierColor: Color
    @Composable get() = when {
        characterLevel < 5 -> uncommonTokenColor
        characterLevel < 11 -> rareTokenColor
        characterLevel < 17 -> veryRareTokenColor
        else -> legendaryTokenColor
    }
