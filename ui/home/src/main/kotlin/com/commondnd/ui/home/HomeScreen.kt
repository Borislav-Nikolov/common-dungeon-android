package com.commondnd.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.commondnd.data.player.Player

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    playerData: Player
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = playerData.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Level ${playerData.playerLevel}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = " • ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = playerData.playerRole,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Status: ${playerData.playerStatus}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Sessions on Level ${playerData.playerLevel}: ${playerData.sessionsOnThisLevel}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tokens",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                TokenRow("Common", playerData.commonTokens)
                TokenRow("Uncommon", playerData.uncommonTokens)
                TokenRow("Rare", playerData.rareTokens)
                TokenRow("Very Rare", playerData.veryRareTokens)
                TokenRow("Legendary", playerData.legendaryTokens)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${playerData.characters?.size ?: 0}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Characters")
                }
            }

            Card(
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${playerData.inventory?.size ?: 0}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Items")
                }
            }
        }
    }
}

@Composable
private fun TokenRow(name: String, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Text(
            text = count.toString(),
            fontWeight = FontWeight.Bold
        )
    }
}
