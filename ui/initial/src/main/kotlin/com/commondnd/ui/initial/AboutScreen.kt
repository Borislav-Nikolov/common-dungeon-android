package com.commondnd.ui.initial

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val expandedSections = remember { mutableStateSetOf<Any>() }
    LazyColumn(
        modifier = modifier
    ) {
        items(
            sections,
            key = { it.id }
        ) { section ->
            val isExpanded = section.id in expandedSections
            Card(
                modifier = Modifier.fillParentMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    with(section) {
                        ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                            SectionHeader()
                        }
                    }
                    IconButton(
                        onClick = {
                            if (isExpanded) {
                                expandedSections.remove(section.id)
                            } else {
                                expandedSections.add(section.id)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                        )
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    visible = isExpanded
                ) {
                    ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                            with(section) {
                                SectionContent()
                            }
                        }
                    }
                }
            }
        }
    }
}

private interface LearnMoreSection {

    val id: Any

    @Composable
    fun RowScope.SectionHeader()

    @Composable
    fun BoxScope.SectionContent()
}

private val sections = listOf<LearnMoreSection>(
    object : LearnMoreSection {

        override val id: String = "what_is_commondungeon_section"

        @Composable
        override fun RowScope.SectionHeader() {
            Text("What is CommonDungeon?")
        }

        @Composable
        override fun BoxScope.SectionContent() {
            Text("CommonDungeon is a community server on Discord. We play D&D and other TTRPGs together.")
        }
    }
)
