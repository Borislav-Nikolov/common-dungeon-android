# add-item-icons

Convert SVG files from item_svgs/ to Android vector drawables and update ItemUtils.kt

## Description

This skill converts SVG files in the `item_svgs/` directory to Android vector drawable XML files and adds them to the ItemUtils.kt mapping. It follows the established naming conventions and automatically updates the keyword mappings.

## Instructions

When this skill is invoked:

1. **Scan for SVG files**
   - List all SVG files in `item_svgs/` directory
   - If no SVG files found, inform the user and exit

2. **Determine naming for each SVG**
   - Extract the base item type from each filename
   - For compound words (e.g., "broadsword"), extract the root (e.g., "sword")
   - If multiple files share the same root, keep the full name
   - Format: `ic_item_[name]` where name is lowercase with underscores
   - Show the user the proposed mapping (e.g., "book-cover.svg -> ic_item_book.xml")

3. **Create Python conversion script**
   - Create a temporary Python script that:
     - Parses each SVG file using xml.etree.ElementTree
     - Extracts viewBox dimensions (default to 512x512)
     - Creates Android vector drawable XML with proper structure:
       ```xml
       <?xml version="1.0" encoding="utf-8"?>
       <vector xmlns:android="http://schemas.android.com/apk/res/android"
           android:width="[width]dp"
           android:height="[height]dp"
           android:viewportWidth="[width]"
           android:viewportHeight="[height]">
           <path android:pathData="[path data]" android:fillColor="[fill color]" />
       </vector>
       ```
     - Skips transparent background paths (fill-opacity="0" or "0.001")
     - Uses #FFFFFF as default fill color
     - Outputs to `ui/core/src/main/res/drawable/ic_item_[name].xml`

4. **Run the conversion script**
   - Execute the Python script
   - Report success/failure for each file
   - Delete the temporary script when done

5. **Update ItemUtils.kt**
   - Read `ui/core/src/main/kotlin/com/commondnd/ui/core/ItemUtils.kt`
   - For each new item:
     - Add a private keyword list constant (e.g., `private val BOOK_KEYWORDS = listOf("book", "tome", "grimoire", ...)`)
     - Suggest relevant synonyms for the item type based on common D&D/RPG terminology
     - Ask user to confirm or modify the keywords if needed
   - Add new when conditions before the `else` branch:
     ```kotlin
     BOOK_KEYWORDS.any { normalized.contains(it) } -> R.drawable.ic_item_book
     ```
   - Maintain alphabetical/logical ordering

6. **Summary**
   - Report total number of icons converted
   - List all new drawable resources created
   - Confirm ItemUtils.kt was updated successfully

## Naming conventions

- Use root item type when unique: "broadsword" → "ic_item_sword" (if only sword type)
- Keep full name when multiple similar items exist: "leather-armor" and "plate-armor" → keep both full names
- Replace hyphens with underscores: "book-cover" → "book_cover"
- All lowercase

## Common item type synonyms

Suggest these synonyms when creating keyword lists:
- **book**: tome, grimoire, manual, scroll, spellbook
- **potion**: elixir, flask, vial, tonic, brew, draught
- **sword**: blade, broadsword, longsword, greatsword
- **dagger**: knife, shiv, dirk
- **armor**: armour, chestplate, breastplate, cuirass, mail, chainmail
- **ring**: band, circlet
- **robe**: cloak, mantle, vestment, garment
- **wand**: rod
- **staff**: quarterstaff, stave, cane
- **bag**: sack, pouch, backpack, pack, satchel
- **shield**: buckler
- **pendant**: necklace, amulet, medallion, charm
- **boots**: boot, footwear, shoes, greaves
- **gloves**: glove, handwear
- **helm**: helmet, headgear, casque
- **bottle**: container, jar

## Example usage

```
/add-item-icons
```

The skill will automatically find SVG files in `item_svgs/`, convert them, and update the codebase.
