package com.commondnd

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commondnd.ui.material3.CommonDungeonMaterialTheme
import com.commondnd.ui.navigation.CommonDungeonNavDisplay

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val darkTheme = isDarkTheme()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ) { darkTheme },
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = lightScrim,
                darkScrim = darkScrim,
            ) { darkTheme },
        )
        setContent {
            CommonDungeonMaterialTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding()
                ) {
                    var currentGroup by rememberSaveable { mutableStateOf("NavGraphGroup.NoUserGroup") }
                    CommonDungeonNavDisplay(
                        currentGroup = currentGroup,
                        startDestination = if (currentGroup == "NavGraphGroup.NoUserGroup") "Initial" else "Home",
                        registry = {
                            register(
                                group = "NavGraphGroup.NoUserGroup",
                                key = "Initial",
                                content = { navController ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Welcome to CommonDungeon")
                                        Button(onClick = {
                                            navController.navigate("Login")
                                        }) {
                                            Text("Log in")
                                        }
                                        Button(onClick = {
                                            navController.navigate("About")
                                        }) {
                                            Text("About")
                                        }
                                    }
                                }
                            )
                            register(
                                group = "NavGraphGroup.NoUserGroup",
                                key = "Login",
                                content = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("This is the login page")
                                        Button(onClick = {
                                            currentGroup = "UserScope"
                                        }) {
                                            Text("Log in")
                                        }
                                    }
                                }
                            )
                            register(
                                group = "NavGraphGroup.NoUserGroup",
                                key = "About",
                                content = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("This describes what the app is about.")
                                    }
                                }
                            )
                            register(
                                group = "UserScope",
                                key = "Home",
                                content = { navController ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("This is the HOME page")
                                        Button(onClick = {
                                            navController.navigate("Characters")
                                        }) {
                                            Text("Characters")
                                        }
                                    }
                                }
                            )
                            register(
                                group = "UserScope",
                                key = "Characters",
                                content = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("This is the CHARACTERS page")
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    fun isDarkTheme(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
