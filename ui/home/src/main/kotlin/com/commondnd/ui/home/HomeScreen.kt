package com.commondnd.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.commondnd.data.player.Player
import com.commondnd.data.user.User

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: User?,
    playerData: Player
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = user?.avatar,
            contentDescription = null
        )
    }
}
