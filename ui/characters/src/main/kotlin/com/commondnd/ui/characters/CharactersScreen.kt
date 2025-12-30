package com.commondnd.ui.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.commondnd.data.character.DndClass
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.player.Player
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.ExperienceBar
import com.commondnd.ui.core.R
import com.commondnd.ui.core.icon
import com.commondnd.ui.core.tierColor

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    player: Player
) {
    player.characters?.let { characters ->
        val expandedSections = remember { mutableStateSetOf<String>() }
        val characterKey: PlayerCharacter.() -> String = remember {
            {
                "${characterName}_${classes.joinToString(separator = "_") { it.className }}"
            }
        }
        LazyColumn(
            modifier = modifier
        ) {
            items(
                characters,
                key = { it.characterKey() }
            ) { character ->
                val key = remember(character) { character.characterKey() }
                ExpandableCard(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    isExpanded = key in expandedSections,
                    onExpand = { expandedSections.add(key) },
                    onCollapse = { expandedSections.remove(key) },
                    headerContent = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(56.dp),
                                painter = character.getIcon(),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(character.tierColor)
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                text = character.characterName,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    expandedContent = {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleMedium,
                                text = stringResource(R.string.label_level_format, character.characterLevel)
                            )
                            Text(
                                style = MaterialTheme.typography.bodyMedium,
                                text = character.classes.joinToString(separator = " • ") { "${it.className} ${it.level}" }
                            )
                            ExperienceBar(
                                isAtMax = character.maxLevel == character.characterLevel,
                                currentProgress = character.sessionsOnThisLevel,
                                maxProgress = character.sessionsToNextLevel
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun PlayerCharacter.getIcon(): Painter = DndClass.fromString(
    classes.first { it.isPrimary }.className
)?.icon ?: painterResource(R.drawable.logo_bright_dawn_color)

private val PlayerCharacter.sessionsToNextLevel: Int
    get() = when {
        characterLevel < 3 -> 1
        characterLevel < 5 -> 2
        else -> 3
    }
