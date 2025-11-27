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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import com.commondnd.ui.material3.CommonDungeonMaterialTheme
import com.commondnd.ui.navigation.CommonDungeonNavDisplay
import com.commondnd.ui.navigation.NavGraphGroup
import com.commondnd.ui.navigation.NavGraphKey

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
                    CommonDungeonNavDisplay(
                        currentGroup = NavGraphGroup.NoUserGroup,
                        startDestination = NavGraphKey.Initial,
                        entryProvider = { group ->
                            // TODO: remove the dependency to the navigation3 library from the app module
                            entryProvider {
                                when (group) {
                                    NavGraphGroup.NoUserGroup -> {
                                        entry<NavGraphKey.Initial> {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("Welcome to CommonDungeon")
                                                Button(onClick = {
                                                    navigate(NavGraphKey.Login)
                                                }) {
                                                    Text("Log in")
                                                }
                                                Button(onClick = {
                                                    navigate(NavGraphKey.About)
                                                }) {
                                                    Text("About")
                                                }
                                            }
                                        }
                                        entry<NavGraphKey.Login> {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("This is the login page")
                                            }
                                        }
                                        entry<NavGraphKey.About> {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text("This describes what the app is about.")
                                            }
                                        }
                                    }
                                    NavGraphGroup.UserGroup -> TODO()
                                }
                            }
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
