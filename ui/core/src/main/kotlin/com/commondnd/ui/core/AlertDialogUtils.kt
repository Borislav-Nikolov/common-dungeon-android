package com.commondnd.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun BasicConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    confirmLabel: String = stringResource(R.string.label_ok),
    dismissLabel: String = stringResource(R.string.label_cancel),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = description?.let {
            {
                Text(it)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(dismissLabel)
            }
        }
    )
}

@Composable
fun <O> RadioButtonDialog(
    modifier: Modifier = Modifier,
    title: String,
    confirmLabel: String,
    dismissLabel: String,
    options: List<O>,
    selectedOption: O,
    optionLabel: @Composable (O) -> String,
    onOptionSelected: (O) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { onOptionSelected(option) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null // handled by Row's selectable
                        )
                        Text(
                            modifier = Modifier.padding(start = 12.dp),
                            style = MaterialTheme.typography.labelMedium,
                            text = optionLabel(option)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(dismissLabel)
            }
        }
    )
}
