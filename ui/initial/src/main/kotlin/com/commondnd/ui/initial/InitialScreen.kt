package com.commondnd.ui.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                onLoginClick = {},
                onViewPrivacyPolicy = {},
                onAboutClick = {}
            )
        }
    }
}

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onViewPrivacyPolicy: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .sizeIn(
                    minWidth = 80.dp,
                    minHeight = 80.dp,
                    maxWidth = 200.dp,
                    maxHeight = 200.dp
                ),
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
        var privacyPolicyAccepted: Boolean by remember { mutableStateOf(false) }
        Card(
            modifier = Modifier.padding(bottom = 16.dp)
        ){
            Column {
                Row(
                    modifier = Modifier.clickable(
                        onClick = {
                            privacyPolicyAccepted = !privacyPolicyAccepted
                        }
                    ).padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = privacyPolicyAccepted,
                        onCheckedChange = {
                            privacyPolicyAccepted = it
                        }
                    )
                    Text(text = stringResource(R.string.description_accept_privacy_policy))
                }
                OutlinedButton(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).align(Alignment.End),
                    onClick = onViewPrivacyPolicy
                ) {
                    Text(text = stringResource(R.string.label_read_privacy_policy))
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLoginClick,
            enabled = privacyPolicyAccepted
        ) {
            Text(text = stringResource(R.string.label_log_in_with_discord))
        }
        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAboutClick
        ) {
            Text(text = stringResource(R.string.label_learn_more))
        }
    }
}
