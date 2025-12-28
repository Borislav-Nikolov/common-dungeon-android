package com.commondnd.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.Token
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DiscordAvatar(user = user)
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(R.string.label_level_format, playerData.playerLevel)
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(),
                    progress = { playerData.sessionsOnThisLevel.toFloat() / 6 }
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(R.string.label_role_format, playerData.playerRole)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(R.string.label_status_format, playerData.playerStatus)
                    )
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.label_tokens)
                )
                IconButton(
                    onClick = onExchangeTokens
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CurrencyExchange,
                        contentDescription = stringResource(R.string.content_description_exchange_tokens)
                    )
                }
            }
            repeat(5) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Token,
                        contentDescription = null,
                        tint = it.tokenColor
                    )
                    Text(
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        text = "${it.tokenText}:",
                    )
                    Text(
                        text = it.tokenCount(playerData).toString()
                    )
                }
            }
        }
    }
}

private val Int.tokenColor: Color
    get() = when (this) {
        0 -> commonTokenColor
        1 -> uncommonTokenColor
        2 -> rareTokenColor
        3 -> veryRareTokenColor
        4 -> legendaryTokenColor
        else -> throw IllegalArgumentException()
    }

private val Int.tokenText: String
    @Composable get() = stringResource(
        when (this) {
            0 -> R.string.common_tokens
            1 -> R.string.uncommon_tokens
            2 -> R.string.rare_tokens
            3 -> R.string.very_rare_tokens
            4 -> R.string.legendary_tokens
            else -> throw IllegalArgumentException()
        }
    )

private fun Int.tokenCount(player: Player) = when (this) {
    0 -> player.commonTokens
    1 -> player.uncommonTokens
    2 -> player.rareTokens
    3 -> player.veryRareTokens
    4 -> player.legendaryTokens
    else -> throw IllegalArgumentException()
}
