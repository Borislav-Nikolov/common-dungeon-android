package com.commondnd.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CompareArrows
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commondnd.data.character.PlayerCharacter
import kotlin.collections.forEach

@Composable
fun <T, O : BottomSheetOption<T, O>> BottomSheetOptionRow(
    modifier: Modifier = Modifier,
    option: O,
    imageVector: ImageVector,
    label: String,
    onOption: (O) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onOption(option) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(16.dp),
            imageVector = imageVector,
            contentDescription = null
        )
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = label
        )
    }
}

interface BottomSheetOption<T, O : BottomSheetOption<T, O>> {

    fun content(
        item: T,
        onOption: (O) -> Unit
    ): (@Composable () -> Unit)?
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T, O : BottomSheetOption<T, O>> SettingsBottomSheet(
    modifier: Modifier = Modifier,
    sheetDataState: BottomSheetDataState<T>,
    options: List<O>,
    onOption: (O, T) -> Unit
) {
    StatefulBottomSheet(
        modifier = modifier,
        onDismissRequest = { sheetDataState.data = null },
        sheetDataState = sheetDataState
    ) { dataItem: T? ->
        dataItem?.let {
            options.forEach {
                it.content(dataItem) { option -> onOption(option, dataItem) }?.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> StatefulBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetDataState: BottomSheetDataState<T>,
    sheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    sheetGesturesEnabled: Boolean = true,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    content: @Composable ColumnScope.(T?) -> Unit,
) {
    if (sheetDataState.show) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            sheetState = sheetState,
            sheetMaxWidth = sheetMaxWidth,
            sheetGesturesEnabled = sheetGesturesEnabled,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = dragHandle,
            contentWindowInsets = contentWindowInsets,
            properties = properties,
            content = {
                content(sheetDataState.data)
            }
        )
    }
}

@Composable
fun <T> rememberBottomSheetDataState(): BottomSheetDataState<T> =
    remember { DataBottomSheetDataState() }

interface BottomSheetDataState<T> {

    var data: T?

    val show: Boolean
}

class DataBottomSheetDataState<T> : BottomSheetDataState<T> {

    override var data: T? by mutableStateOf(null)
    override val show: Boolean by derivedStateOf { data != null }
}
