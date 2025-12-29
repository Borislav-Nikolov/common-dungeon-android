package com.commondnd.ui.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.player.Player
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.ExperienceBar

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    player: Player
) {
    player.characters?.let { characters ->
        val expandedSections = remember { mutableStateSetOf<String>() }
        val characterKey: PlayerCharacter.() -> String = remember {
            {
                "${characterName}_${classes.joinToString { it.className }}"
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
                    onExpand = {
                        // TODO: fix a bug where upon "recycling" later elements also get the expanded condition
                        expandedSections.add(key) },
                    onCollapse = { expandedSections.remove(key) },
                    headerContent = {
                        Image(
                            modifier = Modifier.size(64.dp),
                            // TODO: add class icons or something
                            painter = painterResource(com.commondnd.ui.core.R.drawable.logo_bright_dawn_color),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = character.characterName,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    expandedContent = {
                        ExperienceBar(
                            // TODO: use max level (not implemented in models) to fill the progress bar and hide numbers
                            currentProgress = character.sessionsOnThisLevel,
                            maxProgress = character.sessionsToNextLevel
                        )
                    }
                )
            }
        }
    }
}

private val PlayerCharacter.sessionsToNextLevel: Int
    get() = when {
        characterLevel < 3 -> 1
        characterLevel < 5 -> 2
        else -> 3
    }
