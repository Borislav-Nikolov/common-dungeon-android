package com.commondnd.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.commondnd.data.core.Rarity

val Rarity.labelResource: Int
    get() = when (this) {
        Rarity.Common -> R.string.label_common
        Rarity.Uncommon -> R.string.label_uncommon
        Rarity.Rare -> R.string.label_rare
        Rarity.VeryRare -> R.string.label_very_rare
        Rarity.Legendary -> R.string.label_legendary
    }

val Rarity.label: String
    @Composable get() = stringResource(labelResource)
