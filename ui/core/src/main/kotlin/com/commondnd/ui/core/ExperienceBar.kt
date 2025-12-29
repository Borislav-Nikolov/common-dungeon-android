package com.commondnd.ui.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExperienceBar(
    modifier: Modifier = Modifier,
    currentProgress: Int,
    maxProgress: Int
) {
    LaunchedEffect(currentProgress, maxProgress) {
        require(currentProgress <= maxProgress) { "EXP bar - current progress $currentProgress cannot be more than max progress $maxProgress" }
    }
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        var progressHeight: Dp by remember { mutableStateOf(16.dp) }
        LinearProgressIndicator(
            modifier = Modifier
                .height(progressHeight)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            progress = { currentProgress.toFloat() / maxProgress },
            gapSize = 0.dp,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {}
        )
        val density = LocalDensity.current
        Text(
            modifier = Modifier.onSizeChanged { size ->
                val heightPx = size.width
                val heightDp = with(density) { heightPx.toDp() }
                progressHeight = heightDp
            },
            style = MaterialTheme.typography.labelSmall,
            text = "$currentProgress / $maxProgress",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
