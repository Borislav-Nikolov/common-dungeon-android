package com.commondnd.ui.initial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun InitialScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Welcome to CommonDungeon")
        Button(onClick = onLoginClick) {
            Text("Log in")
        }
        Button(onClick = onAboutClick) {
            Text("About")
        }
    }
}
