package com.commondnd.ui.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.commondnd.ui.core.getVersionName

@Preview
@Composable
private fun MoreScreenPreview() {
    MoreScreen(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
        Box(
            modifier = Modifier.weight(0.5f),
        ) {
            Button(
                onClick = onLogout
            ) {
                Text(stringResource(R.string.label_log_out))
            }
        }
        val context = LocalContext.current
        val versionLabel = stringResource(R.string.label_version)
        Text(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            text = remember(context) { "$versionLabel: ${context.getVersionName()}" }
        )
    }
}
