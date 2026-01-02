package com.commondnd.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
    doTokenConversion: (from: Rarity, to: Rarity, value: Int) -> Unit
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
        RaritySelectionRow(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            style = MaterialTheme.typography.titleMedium,
            text = "${stringResource(R.string.title_token_conversion_result)}:"
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = when (val state = calculationState) {
                is State.Loaded<TokenConversionResult> -> stringResource(R.string.token_subtraction_result_format, fromSelected.label, state.value.subtracted)
                else -> "..."
            }
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = when (val state = calculationState) {
                is State.Loaded<TokenConversionResult> -> stringResource(R.string.token_addition_result_format, toSelected.label, state.value.added)
                else -> "..."
            }
        )
        Button(
            onClick = {
                doTokenConversion(
                    fromSelected,
                    toSelected,
                    value.toInt()
                )
            },
            enabled = conversionState !is State.Loading
        ) {
            Text(text = stringResource(R.string.label_do_conversion))
        }
        TokensCard(
            modifier = Modifier.fillMaxWidth(),
            playerData = player
        )
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
    Row(
        modifier = modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        Text(
            text = "$label:"
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
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
