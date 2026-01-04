package com.commondnd.ui.initial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.ExpandableCardSection
import com.commondnd.ui.material3.CommonDungeonMaterialTheme

@Preview
@Composable
private fun AboutScreenPreview() {
    CommonDungeonMaterialTheme {
        AboutScreen(
            modifier = Modifier.fillMaxSize(),
            onBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val expandedSections = remember { mutableStateSetOf<Any>() }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.label_learn_more))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(
                                com.commondnd.ui.core.R.string.content_description_navigate_back
                            )
                        )
                    }
                }
            )
        },
        content = { contentPadding ->
            LazyColumn(
                modifier = Modifier.padding(contentPadding)
            ) {
                items(
                    sections,
                    key = { it.id }
                ) { section ->
                    ExpandableCard(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        section = section,
                        isExpanded = section.id in expandedSections,
                        onExpand = { expandedSections.add(section.id) },
                        onCollapse = { expandedSections.remove(section.id) }
                    )
                }
            }
        }
    )
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
