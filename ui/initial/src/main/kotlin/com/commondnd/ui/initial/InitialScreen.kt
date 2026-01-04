package com.commondnd.ui.initial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.commondnd.ui.core.BasicScreen
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
    BasicScreen(
        modifier = modifier,
        image = painterResource(com.commondnd.ui.core.R.drawable.logo_bright_dawn_color),
        title = stringResource(R.string.title_initial_screen),
        subtitle = stringResource(R.string.description_initial_screen),
        primaryActionLabel = stringResource(R.string.label_log_in_with_discord),
        primaryAction = onLoginClick,
        secondaryActionLabel = stringResource(R.string.label_learn_more),
        secondaryAction = onAboutClick
    )
}
