package com.commondnd

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.auth.AuthTabIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commondnd.data.core.State
import com.commondnd.ui.characters.registerCharactersScreens
import com.commondnd.ui.core.NetworkState
import com.commondnd.ui.core.NoNetworkBanner
import com.commondnd.ui.core.networkState
import com.commondnd.ui.core.rememberNetworkState
import com.commondnd.ui.home.registerHomeScreens
import com.commondnd.ui.initial.registerInitialScreens
import com.commondnd.ui.inventory.registerInventoryScreens
import com.commondnd.ui.login.AuthResult
import com.commondnd.ui.material3.CommonDungeonMaterialTheme
import com.commondnd.ui.more.registerMoreScreens
import com.commondnd.ui.navigation.CommonDungeonNavDisplay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val loginLauncher = AuthTabIntent.registerActivityResultLauncher(this) { authResult ->

        Log.d(
            LOGIN_TAG,
            when (authResult.resultCode) {
                AuthTabIntent.RESULT_OK -> "Received auth result. Uri: ${authResult.resultUri}"
                AuthTabIntent.RESULT_CANCELED -> "AuthTab canceled."
                AuthTabIntent.RESULT_VERIFICATION_FAILED -> "Verification failed."
                AuthTabIntent.RESULT_VERIFICATION_TIMED_OUT -> "Verification timed out."
                else -> "Unknown result"
            }
        )

        mainViewModel.finishAuth(
            code = authResult.resultUri?.getQueryParameter("code"),
            authResult = when (authResult.resultCode) {
                AuthTabIntent.RESULT_OK -> AuthResult.Success
                AuthTabIntent.RESULT_CANCELED -> AuthResult.Cancelled
                AuthTabIntent.RESULT_VERIFICATION_FAILED -> AuthResult.VerificationFailed
                AuthTabIntent.RESULT_VERIFICATION_TIMED_OUT -> AuthResult.Timeout
                else -> AuthResult.Unknown
            }
        )
    }

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
            val networkState: NetworkState = rememberNetworkState()
            CommonDungeonMaterialTheme(darkTheme = darkTheme) {
                val hasBottomNavigation by mainViewModel.hasBottomNavigation.collectAsState()
                val currentGroup by mainViewModel.currentGroup.collectAsState(null)
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = if (hasBottomNavigation) ScaffoldDefaults.contentWindowInsets else WindowInsets.statusBars,
                    topBar = {
                        AnimatedVisibility(networkState == NetworkState.Unavailable) {
                            NoNetworkBanner(modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding())
                        }
                    },
                    content = { contentPadding ->
                        val dataSyncState by mainViewModel.dataSyncState.collectAsState()
                        LaunchedEffect(dataSyncState) {
                            if (dataSyncState is State.Error) {
                                Toast.makeText(
                                    this@MainActivity,
                                    R.string.error_could_not_sync_data,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        PullToRefreshBox(
                            isRefreshing = dataSyncState is State.Loading,
                            onRefresh = { mainViewModel.syncData() },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding)
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CommonDungeonNavDisplay(
                                    groupedNavController = mainViewModel,
                                    registry = {
                                        registerInitialScreens(
                                            loginController = mainViewModel,
                                            onLoginRequest = { uri, redirectUri, codeVerifier ->
                                                AuthTabIntent.Builder().build().run {
                                                    launch(
                                                        loginLauncher,
                                                        uri,
                                                        redirectUri.scheme!!
                                                    )
                                                }
                                                mainViewModel.startAuth(codeVerifier, redirectUri)
                                            }
                                        )
                                        registerHomeScreens()
                                        registerCharactersScreens()
                                        registerInventoryScreens()
                                        registerMoreScreens()
                                    }
                                )
                            }
                        }
                    },
                    bottomBar = {
                        AnimatedVisibility(hasBottomNavigation) {
                            NavigationBar {
                                mainViewModel.navigationTabs.forEach {
                                    NavigationBarItem(
                                        selected = it == currentGroup,
                                        onClick = { mainViewModel.makeCurrent(it) },
                                        icon = {
                                            Icon(
                                                modifier = Modifier.sizeIn(maxHeight = 24.dp),
                                                painter = iconMap(it),
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(labelMap(it)) }
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    fun isDarkTheme(): Boolean {
        return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    companion object {

        private const val LOGIN_TAG = "Login"
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
