package com.commondnd.ui.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.commondnd.ui.material3.CommonDungeonMaterialTheme

@Preview
@Composable
fun InitialScreenPreview() {
    CommonDungeonMaterialTheme {
        Surface {
            InitialScreen(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                onLoginClick = {},
                onAboutClick = {}
            )
        }
    }
}

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(com.commondnd.ui.core.R.drawable.logo_bright_dawn_color),
            contentDescription = null
        )
        Text(
            maxLines = 1,
            style = MaterialTheme.typography.headlineLarge,
            autoSize = TextAutoSize.StepBased(),
            text = stringResource(R.string.title_initial_screen)
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.description_initial_screen)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLoginClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    painter = painterResource(com.commondnd.ui.core.R.drawable.ic_discord_logo),
                    contentDescription = null
                )
                Text(modifier = Modifier.padding(start = 16.dp), text = stringResource(R.string.label_log_in_with_discord))
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAboutClick
        ) {
            Text(stringResource(R.string.label_learn_more))
        }
    }
}
