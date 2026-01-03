package com.commondnd.ui.core

import com.commondnd.data.core.Rarity
import com.commondnd.data.player.Player

fun Player.getTokenCount(rarity: Rarity): Int = when (rarity) {
    Rarity.Common -> commonTokens
    Rarity.Uncommon -> uncommonTokens
    Rarity.Rare -> rareTokens
    Rarity.VeryRare -> veryRareTokens
    Rarity.Legendary -> legendaryTokens
}
