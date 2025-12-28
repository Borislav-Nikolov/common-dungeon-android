package com.commondnd.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commondnd.data.player.Player
import com.commondnd.data.user.User
import com.commondnd.ui.core.DiscordAvatar

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
        DiscordAvatar(user)
    }
}
