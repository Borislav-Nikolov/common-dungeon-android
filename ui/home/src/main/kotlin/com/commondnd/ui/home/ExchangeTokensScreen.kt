package com.commondnd.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.commondnd.data.core.Rarity
import com.commondnd.data.core.State
import com.commondnd.data.player.Player
import com.commondnd.data.player.TokenConversionResult
import com.commondnd.ui.core.label

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeTokensScreen(
    modifier: Modifier = Modifier,
    player: Player,
    calculationState: State<TokenConversionResult>,
    conversionState: State<Unit>,
    calculateTokenConversion: (from: Rarity, to: Rarity, value: Int) -> Unit,
    doTokenConversion: (from: Rarity, to: Rarity, value: Int) -> Unit,
    onBack: () -> Unit
) {
    var fromSelected: Rarity by remember {
        mutableStateOf(Rarity.Uncommon)
    }
    var toSelected: Rarity by remember {
        mutableStateOf(Rarity.Common)
    }
    var value: String by remember { mutableStateOf("1") }
    val fromOptions = remember(toSelected) {
        toSelected.getAllGreater()
    }
    val toOptions = remember(fromSelected) {
        fromSelected.getAllLesser()
    }
    Column(
        modifier = modifier
    ) {
        TopAppBar(
            windowInsets = remember { WindowInsets() },
            title = {
                Text(stringResource(R.string.title_convert_tokens))
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(com.commondnd.ui.core.R.string.content_description_navigate_back)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .imePadding()
        ) {
            TokensCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                playerData = player
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.label_convert)
            )
            RaritySelectionRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                label = stringResource(R.string.label_from),
                options = fromOptions,
                selected = fromSelected,
                onSelected = {
                    fromSelected = it
                    calculateTokenConversion(
                        fromSelected,
                        toSelected,
                        value.toInt()
                    )
                }
            )
            RaritySelectionRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                label = stringResource(R.string.label_to),
                options = toOptions,
                selected = toSelected,
                onSelected = {
                    toSelected = it
                    calculateTokenConversion(
                        fromSelected,
                        toSelected,
                        value.toInt()
                    )
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = value,
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                },
                onValueChange = {
                    val numberValue = it.toIntOrNull()
                    if (numberValue != null && numberValue > 0) {
                        value = it
                        calculateTokenConversion(
                            fromSelected,
                            toSelected,
                            numberValue
                        )
                    }
                }
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                text = "${stringResource(R.string.title_token_conversion_result)}:"
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(
                    R.string.token_subtraction_result_format,
                    fromSelected.label,
                    if (calculationState is State.Loaded<TokenConversionResult>) calculationState.value.subtracted else "..."
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(
                    R.string.token_addition_result_format,
                    toSelected.label,
                    if (calculationState is State.Loaded<TokenConversionResult>) calculationState.value.added else "..."
                )
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = {
                    doTokenConversion(
                        fromSelected,
                        toSelected,
                        value.toInt()
                    )
                },
                enabled = conversionState !is State.Loading
            ) {
                Text(text = stringResource(R.string.label_convert))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RaritySelectionRow(
    modifier: Modifier = Modifier,
    label: String,
    options: List<Rarity>,
    selected: Rarity,
    onSelected: (Rarity) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = "$label:"
        )
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                value = selected.name,
                readOnly = true,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}
