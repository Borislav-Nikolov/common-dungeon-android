package com.commondnd.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BasicScreen(
    modifier: Modifier = Modifier,
    image: ImageVector? = null,
    title: String,
    subtitle: String? = null,
    primaryAction: Pair<Any, String>? = null,
    secondaryAction: Pair<Any, String>? = null,
    alternativeAction: Pair<Any, String>? = null,
    tertiaryAction: Pair<Any, String>? = null,
    onAction: ((Any) -> Unit)? = null
) {
    BasicScreen(
        modifier = modifier,
        image = image?.let{ rememberVectorPainter(it) },
        title = title,
        subtitle = subtitle,
        primaryAction = primaryAction,
        secondaryAction = secondaryAction,
        alternativeAction = alternativeAction,
        tertiaryAction = tertiaryAction,
        onAction = onAction
    )
}

@Composable
fun BasicScreen(
    modifier: Modifier = Modifier,
    image: Painter? = null,
    title: String,
    subtitle: String? = null,
    primaryAction: Pair<Any, String>? = null,
    secondaryAction: Pair<Any, String>? = null,
    alternativeAction: Pair<Any, String>? = null,
    tertiaryAction: Pair<Any, String>? = null,
    onAction: ((Any) -> Unit)? = null
) {
    BasicScreen(
        modifier = modifier,
        image = image,
        title = title,
        subtitle = subtitle,
        primaryActionLabel = primaryAction?.second,
        secondaryActionLabel = secondaryAction?.second,
        alternativeActionLabel = alternativeAction?.second,
        tertiaryActionLabel = tertiaryAction?.second,
        primaryAction = primaryAction?.let { { onAction?.invoke(it.first) } },
        secondaryAction = secondaryAction?.let { { onAction?.invoke(it.first) } },
        alternativeAction = alternativeAction?.let { { onAction?.invoke(it.first) } },
        tertiaryAction = tertiaryAction?.let { { onAction?.invoke(it.first) } }
    )
}

@Composable
fun BasicScreen(
    modifier: Modifier = Modifier,
    image: Painter? = null,
    title: String,
    subtitle: String? = null,
    primaryActionLabel: String? = null,
    secondaryActionLabel: String? = null,
    alternativeActionLabel: String? = null,
    tertiaryActionLabel: String? = null,
    primaryAction: (() -> Unit)? = null,
    secondaryAction: (() -> Unit)? = null,
    alternativeAction: (() -> Unit)? = null,
    tertiaryAction: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        image?.let {
            Image(
                modifier = Modifier.weight(1f).sizeIn(minWidth = 80.dp, minHeight = 80.dp, maxWidth = 200.dp, maxHeight = 200.dp),
                painter = it,
                contentDescription = null
            )
        }
        val titleModifier = if (subtitle == null) Modifier.weight(1f) else Modifier
        Text(
            modifier = titleModifier,
            maxLines = 1,
            style = MaterialTheme.typography.headlineLarge,
            autoSize = TextAutoSize.StepBased(),
            text = title
        )
        subtitle?.let {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                text = it
            )
        }
        if (primaryAction != null && primaryActionLabel != null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = primaryAction
            ) {
                Text(text = primaryActionLabel)
            }
        }
        if (secondaryAction != null && secondaryActionLabel != null) {
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = secondaryAction
            ) {
                Text(text = secondaryActionLabel)
            }
        }
        if (alternativeAction != null && alternativeActionLabel != null) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = alternativeAction
            ) {
                Text(text = alternativeActionLabel)
            }
        }
        if (tertiaryAction != null && tertiaryActionLabel != null) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = tertiaryAction
            ) {
                Text(text = tertiaryActionLabel)
            }
        }
    }
}
