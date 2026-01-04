package com.commondnd.ui.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.commondnd.data.user.User

@Composable
fun DiscordAvatar(
    modifier: Modifier = Modifier,
    user: User?
) {
    DiscordAvatar(
        modifier = modifier,
        user?.id,
        user?.avatar
    )
}

@Composable
fun DiscordAvatar(
    modifier: Modifier = Modifier,
    userId: String?,
    userAvatar: String?
) {
    AsyncImage(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ),
        model = "https://cdn.discordapp.com/avatars/$userId/$userAvatar.png",
        contentDescription = null,
        placeholder = ColorPainter(Color.Gray),
        error = ColorPainter(Color.Gray)
    )
}
