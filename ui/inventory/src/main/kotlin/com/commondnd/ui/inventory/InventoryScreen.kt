package com.commondnd.ui.inventory

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Sell
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.commondnd.data.core.Rarity
import com.commondnd.data.item.InventoryItem
import com.commondnd.data.player.Player
import com.commondnd.ui.core.BottomSheetDataState
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.core.ErrorSpec
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.StatefulBottomSheet
import com.commondnd.ui.core.icon
import com.commondnd.ui.core.label
import com.commondnd.ui.core.rarityColor
import com.commondnd.ui.core.rememberBottomSheetDataState
import com.commondnd.ui.core.tierColor

@Composable
fun InventoryScreen(
    modifier: Modifier = Modifier,
    player: Player
) {
    if (player.inventory != null) {
        val sheetDataState = rememberBottomSheetDataState<InventoryItem>()
        InventoryList(
            modifier = modifier,
            inventory = player.inventory!!,
            onSettingsClick = { sheetDataState.data = it },
        )
        ItemSettingsBottomSheet(sheetDataState = sheetDataState)
    } else {
        ErrorScreen(
            errorSpec = ErrorSpec(
                title = stringResource(R.string.title_error_inventory_could_not_be_loaded)
            )
        )
    }
}

@Composable
private fun InventoryList(
    modifier: Modifier,
    inventory: List<InventoryItem>,
    onSettingsClick: (InventoryItem) -> Unit
) {
    val expandedSections = remember { mutableStateSetOf<String>() }
    val inventoryKey: InventoryItem.() -> String = remember { { "${name}_${index}" } }
    LazyColumn(
        modifier = modifier
    ) {
        items(
            inventory,
            key = { it.inventoryKey() }
        ) { item ->
            val key = remember(item) { item.inventoryKey() }
            ExpandableCard(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                isExpanded = key in expandedSections,
                onExpand = { expandedSections.add(key) },
                onCollapse = { expandedSections.remove(key) },
                headerContent = {
                    ItemRowHeader(item = item)
                },
                expandedContent = {
                    ItemExpandedContent(
                        modifier = Modifier.fillMaxWidth(),
                        item = item,
                        onSettingsClick = { onSettingsClick(item) }
                    )
                }
            )
        }
    }
}

@Composable
private fun ItemExpandedContent(
    modifier: Modifier = Modifier,
    item: InventoryItem,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val raritySectionLabel = stringResource(R.string.label_rarity)
            val rarity = remember(item) { Rarity.fromString(item.rarity) }
            val rarityLabel = rarity.label
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = remember(raritySectionLabel, rarityLabel, item) {
                    "${raritySectionLabel}: ${rarityLabel}, ${item.rarityLevel}"
                }
            )
            val quantitySectionLabel = stringResource(R.string.label_quantity)
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = remember(quantitySectionLabel, item) {
                    "${quantitySectionLabel}: ${item.quantity}"
                }
            )
            val sellableSectionLabel = stringResource(R.string.label_sellable)
            val sellableLabel = if (item.sellable) stringResource(
                R.string.label_yes
            ) else stringResource(R.string.label_no)
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = remember(sellableSectionLabel, sellableLabel) {
                    "$sellableSectionLabel: $sellableLabel"
                }
            )
        }
        IconButton(
            modifier = Modifier.padding(start = 4.dp),
            onClick = onSettingsClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = stringResource(com.commondnd.ui.core.R.string.content_description_options)
            )
        }
    }
}

@Composable
private fun ItemRowHeader(
    modifier: Modifier = Modifier,
    item: InventoryItem
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = item.icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(item.rarityColor)
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = item.name,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemSettingsBottomSheet(
    modifier: Modifier = Modifier,
    sheetDataState: BottomSheetDataState<InventoryItem>
) {
    StatefulBottomSheet(
        modifier = modifier,
        onDismissRequest = { sheetDataState.data = null },
        sheetDataState = sheetDataState
    ) { item: InventoryItem? ->
        if (item?.sellable == true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        // TODO:
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(16.dp),
                    imageVector = Icons.Rounded.Sell,
                    contentDescription = null
                )
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(R.string.label_sell)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    // TODO:
                }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = null
            )
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(R.string.label_delete)
            )
        }
    }
}