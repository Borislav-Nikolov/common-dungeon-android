package com.commondnd.ui.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CompareArrows
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.commondnd.data.character.CharacterStatus
import com.commondnd.data.character.DndClass
import com.commondnd.data.character.PlayerCharacter
import com.commondnd.data.player.Player
import com.commondnd.ui.core.BottomSheetDataState
import com.commondnd.ui.core.BottomSheetOption
import com.commondnd.ui.core.BottomSheetOptionRow
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.core.ErrorSpec
import com.commondnd.ui.core.ExpandableCard
import com.commondnd.ui.core.ExperienceBar
import com.commondnd.ui.core.RadioButtonDialog
import com.commondnd.ui.core.SettingsBottomSheet
import com.commondnd.ui.core.icon
import com.commondnd.ui.core.rememberBottomSheetDataState
import com.commondnd.ui.core.rememberSerializable
import com.commondnd.ui.core.tierColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    player: Player,
    onChangeStatus: (CharacterStatus, PlayerCharacter) -> Unit
) {
    if (player.characters != null) {
        val sheetDataState = rememberBottomSheetDataState<PlayerCharacter>()
        CharactersList(
            modifier = modifier,
            characters = player.characters!!,
            onSettingsClick = { sheetDataState.data = it },
        )
        var changeStatusTarget: PlayerCharacter? by rememberSerializable {
            mutableStateOf(null)
        }
        CharacterSettingsBottomSheet(
            sheetDataState = sheetDataState,
            options = CharacterOption.entries,
            onOption = { option, character ->
                when (option) {
                    CharacterOption.ChangeStatus -> {
                        changeStatusTarget = character
                    }
                }
                sheetDataState.data = null
            }
        )
        if (changeStatusTarget != null) {
            ChangeStatusDialog(
                playerCharacter = changeStatusTarget!!,
                onConfirm = { status, character ->
                    onChangeStatus(status, character)
                    changeStatusTarget = null
                },
                onDismiss = {
                    changeStatusTarget = null
                }
            )
        }
    } else {
        ErrorScreen(
            errorSpec = ErrorSpec(
                title = stringResource(R.string.title_characters_could_not_be_loaded)
            )
        )
    }
}

@Composable
private fun ChangeStatusDialog(
    modifier: Modifier = Modifier,
    playerCharacter: PlayerCharacter,
    onConfirm: (CharacterStatus, PlayerCharacter) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(playerCharacter.status) }
    RadioButtonDialog(
        modifier = modifier,
        title = stringResource(R.string.label_change_status),
        optionLabel = { status -> status.label },
        options = CharacterStatus.entries,
        confirmLabel = stringResource(R.string.label_change),
        dismissLabel = stringResource(com.commondnd.ui.core.R.string.label_cancel),
        onConfirm = {
            onConfirm(selectedOption, playerCharacter)
        },
        onDismiss = onDismiss,
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}

@Composable
private fun CharactersList(
    modifier: Modifier,
    characters: List<PlayerCharacter>,
    onSettingsClick: (PlayerCharacter) -> Unit
) {
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
                    CharacterRowHeader(character)
                },
                expandedContent = {
                    CharacterExpandedContent(
                        modifier = Modifier.fillMaxWidth(),
                        character = character,
                        onSettingsClick = { onSettingsClick(character) }
                    )
                }
            )
        }
    }
}

@Composable
private fun CharacterExpandedContent(
    modifier: Modifier = Modifier,
    character: PlayerCharacter,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        CharacterExpandedTopContent(
            modifier = Modifier.fillMaxWidth(),
            character = character,
            onSettingsClick = onSettingsClick
        )
        ExperienceBar(
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
            isAtMax = character.maxLevel == character.characterLevel,
            currentProgress = character.sessionsOnThisLevel,
            maxProgress = character.sessionsToNextLevel
        )
        CharacterExpandedFooterContent(
            modifier = modifier,
            character = character
        )
    }
}

@Composable
private fun CharacterExpandedTopContent(
    modifier: Modifier = Modifier,
    character: PlayerCharacter,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = "${
                    stringResource(
                        com.commondnd.ui.core.R.string.label_level_format,
                        character.characterLevel
                    )
                } / ${character.maxLevel}"
            )
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = character.classes.joinToString(separator = " • ") { "${it.className} ${it.level}" }
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
private fun CharacterExpandedFooterContent(
    modifier: Modifier = Modifier,
    character: PlayerCharacter
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(
                com.commondnd.ui.core.R.string.label_status_format,
                character.status
            )
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(R.string.last_dm_format, character.lastDm),
        )
    }
}

@Composable
private fun CharacterRowHeader(character: PlayerCharacter) {
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
}

private enum class CharacterOption : BottomSheetOption<PlayerCharacter, CharacterOption> {

    ChangeStatus;

    override fun content(
        item: PlayerCharacter,
        onOption: (CharacterOption) -> Unit
    ): (@Composable () -> Unit)? = when (this) {
        ChangeStatus -> {
            {
                BottomSheetOptionRow(
                    option = this,
                    imageVector = Icons.AutoMirrored.Rounded.CompareArrows,
                    label = stringResource(R.string.label_change_status),
                    onOption = onOption
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterSettingsBottomSheet(
    modifier: Modifier = Modifier,
    sheetDataState: BottomSheetDataState<PlayerCharacter>,
    options: List<CharacterOption>,
    onOption: (CharacterOption, PlayerCharacter) -> Unit
) {
    SettingsBottomSheet(
        modifier = modifier,
        sheetDataState = sheetDataState,
        options = options,
        onOption = onOption
    )
}

@Composable
private fun PlayerCharacter.getIcon(): Painter = DndClass.fromString(
    classes.first { it.isPrimary }.className
)?.icon ?: painterResource(com.commondnd.ui.core.R.drawable.logo_bright_dawn_color)

private val PlayerCharacter.sessionsToNextLevel: Int
    get() = when {
        characterLevel < 3 -> 1
        characterLevel < 5 -> 2
        else -> 3
    }

private val CharacterStatus.label: String
    @Composable get() = when(this) {
        CharacterStatus.Active -> stringResource(R.string.label_status_active)
        CharacterStatus.Inactive -> stringResource(R.string.label_status_inactive)
        CharacterStatus.Dead -> stringResource(R.string.label_status_dead)
    }
