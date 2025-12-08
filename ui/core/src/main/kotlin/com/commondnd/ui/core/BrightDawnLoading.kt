package com.commondnd.ui.core

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BrightDawnLoading(
    modifier: Modifier = Modifier
) {
    val iconPainter = painterResource(R.drawable.logo_bright_dawn_color)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val size by infiniteTransition.animateValue(
        initialValue = 200.dp,
        targetValue = 120.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "size"
    )

    Image(
        modifier = modifier.requiredSize(size),
        painter = iconPainter,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
