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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.ExpandableCardSection

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
            ExpandableCard(
                modifier = Modifier.fillParentMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                section = section,
                isExpanded = section.id in expandedSections,
                onExpand = { expandedSections.add(section.id) },
                onCollapse = { expandedSections.remove(section.id) }
            )
        }
    }
}

private val sections = listOf(
    ExpandableCardSection(
        id = "learn_more_section_1",
        header = { Text(stringResource(R.string.title_section_learn_more_1)) },
        content = { Text(stringResource(R.string.description_section_learn_more_1)) }
    ),
    ExpandableCardSection(
        id = "learn_more_section_2",
        header = { Text(stringResource(R.string.title_section_learn_more_2)) },
        content = { Text(stringResource(R.string.description_section_learn_more_2)) }
    )
)
