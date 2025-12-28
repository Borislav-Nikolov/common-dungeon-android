package com.commondnd.ui.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    section: ExpandableCardSection,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onCollapse: () -> Unit
) {
    Card(modifier = modifier) {
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
                        onCollapse()
                    } else {
                        onExpand()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(R.string.content_description_collapse) else stringResource(R.string.content_description_expand),
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

interface ExpandableCardSection {

    val id: Any

    @Composable
    fun RowScope.SectionHeader()

    @Composable
    fun BoxScope.SectionContent()

    companion object {

        operator fun invoke(
            id: Any,
            header: @Composable RowScope.() -> Unit,
            content: @Composable BoxScope.() -> Unit
        ): ExpandableCardSection = object : ExpandableCardSection {
            override val id: Any = id

            @Composable
            override fun RowScope.SectionHeader() {
                header()
            }

            @Composable
            override fun BoxScope.SectionContent() {
                content()
            }
        }
    }
}
