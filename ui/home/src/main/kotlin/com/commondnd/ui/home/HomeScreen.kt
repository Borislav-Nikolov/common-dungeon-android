package com.commondnd.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.Token
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.commondnd.data.player.Player
import com.commondnd.data.user.User
import com.commondnd.ui.core.DiscordAvatar
import com.commondnd.ui.core.ExperienceBar
import com.commondnd.ui.material3.commonTokenColor
import com.commondnd.ui.material3.legendaryTokenColor
import com.commondnd.ui.material3.rareTokenColor
import com.commondnd.ui.material3.uncommonTokenColor
import com.commondnd.ui.material3.veryRareTokenColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: User?,
    playerData: Player,
    onExchangeTokens: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.headlineLarge,
            text = playerData.name
        )
        PlayerInfoRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            user = user,
            playerData = playerData
        )
        TokensCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onExchangeTokens = onExchangeTokens,
            playerData = playerData
        )
    }
}

@Composable
private fun PlayerInfoRow(
    modifier: Modifier = Modifier,
    user: User?,
    playerData: Player
) {
    Row(
        modifier = modifier
    ) {
        DiscordAvatar(
            modifier = Modifier.size(80.dp),
            user = user
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(
                    com.commondnd.ui.core.R.string.label_level_format,
                    playerData.playerLevel
                )
            )
            ExperienceBar(
                currentProgress = playerData.sessionsOnThisLevel,
                maxProgress = 6
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(R.string.label_role_format, playerData.playerRole)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(com.commondnd.ui.core.R.string.label_status_format, playerData.playerStatus)
                )
            }
        }
    }
}
