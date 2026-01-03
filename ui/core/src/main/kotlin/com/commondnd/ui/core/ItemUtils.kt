package com.commondnd.ui.core

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.core.Rarity
import com.commondnd.data.item.InventoryItem
import com.commondnd.ui.material3.commonTokenColor
import com.commondnd.ui.material3.legendaryTokenColor
import com.commondnd.ui.material3.rareTokenColor
import com.commondnd.ui.material3.uncommonTokenColor
import com.commondnd.ui.material3.veryRareTokenColor

private val SWORD_KEYWORDS = listOf("sword", "blade", "broadsword", "longsword", "greatsword")
private val DAGGER_KEYWORDS = listOf("dagger", "knife", "shiv", "dirk")
private val AXE_KEYWORDS = listOf("axe", "hatchet", "battleaxe", "battle-axe", "tomahawk")
private val MACE_KEYWORDS = listOf("mace", "club", "morningstar", "flange", "flanged")
private val CROSSBOW_KEYWORDS = listOf("crossbow", "arbalest")
private val ARROW_KEYWORDS = listOf("arrow", "bolt", "bow")
private val GUN_KEYWORDS = listOf("gun", "pistol", "firearm", "handgun")
private val SLING_KEYWORDS = listOf("sling", "slingshot")
private val TRIDENT_KEYWORDS = listOf("trident", "spear", "pitchfork")
private val FLAIL_KEYWORDS = listOf("flail", "chain")
private val WAND_KEYWORDS = listOf("wand", "rod")
private val STAFF_KEYWORDS = listOf("staff", "quarterstaff", "stave", "cane")
private val ARMOR_KEYWORDS = listOf("armor", "armour", "chestplate", "breastplate", "cuirass", "mail", "chainmail")
private val HELM_KEYWORDS = listOf("helm", "helmet", "headgear", "casque")
private val BARBUTE_KEYWORDS = listOf("barbute", "barb")
private val GAUNTLET_KEYWORDS = listOf("gauntlet", "gauntlets")
private val GLOVES_KEYWORDS = listOf("gloves", "glove", "handwear")
private val BRACERS_KEYWORDS = listOf("bracers", "bracer", "vambrace", "armguard")
private val BOOTS_KEYWORDS = listOf("boots", "boot", "footwear", "shoes", "greaves")
private val SHIELD_KEYWORDS = listOf("shield", "buckler")
private val PENDANT_KEYWORDS = listOf("pendant", "necklace", "amulet", "medallion", "charm")
private val POTION_KEYWORDS = listOf("potion", "elixir", "flask", "vial", "tonic", "brew", "draught")
private val BAG_KEYWORDS = listOf("bag", "sack", "pouch", "backpack", "pack", "satchel")
private val BOOK_KEYWORDS = listOf("book", "tome", "grimoire", "manual", "scroll", "spellbook")
private val ROBE_KEYWORDS = listOf("robe", "cloak", "mantle", "vestment", "garment")
private val BROOM_KEYWORDS = listOf("broom", "broomstick")
private val RING_KEYWORDS = listOf("ring", "band", "circlet")
private val BOTTLE_KEYWORDS = listOf("bottle", "container", "jar")

/**
 * Maps an item name string to its corresponding drawable resource.
 * Supports exact matches and common synonyms.
 *
 * @param itemName The name of the item (case-insensitive)
 * @return The drawable resource ID, or null if no match is found
 */
@DrawableRes
fun getItemIconResource(itemName: String): Int {
    val normalized = itemName.lowercase().trim()
    return when {
        SWORD_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_sword
        DAGGER_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_dagger
        AXE_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_axe
        MACE_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_mace
        CROSSBOW_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_crossbow
        ARROW_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_arrow
        GUN_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_gun
        SLING_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_sling
        TRIDENT_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_trident
        FLAIL_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_flail
        WAND_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_wand
        STAFF_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_staff
        ARMOR_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_armor
        HELM_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_helm
        BARBUTE_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_barbute
        GAUNTLET_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_gauntlet
        GLOVES_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_gloves
        BRACERS_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_bracers
        BOOTS_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_boots
        SHIELD_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_shield
        PENDANT_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_pendant
        POTION_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_potion
        BAG_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_bag
        BOOK_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_book
        ROBE_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_robe
        BROOM_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_broom
        RING_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_ring
        BOTTLE_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_bottle
        else -> R.drawable.ic_item_bag
    }
}

val InventoryItem.icon: Painter
    @Composable get() = painterResource(getItemIconResource(name))

val InventoryItem.rarityColor: Color
    @Composable get() = when(Rarity.fromString(rarity)) {
        Rarity.Common -> commonTokenColor
        Rarity.Uncommon -> uncommonTokenColor
        Rarity.Rare -> rareTokenColor
        Rarity.VeryRare -> veryRareTokenColor
        Rarity.Legendary -> legendaryTokenColor
    }
