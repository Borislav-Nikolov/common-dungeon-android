package com.commondnd.ui.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.networkState() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // Emit initial state
    trySend(connectivityManager.currentNetworkState())
    // Start monitoring
    val networkCallback = networkCallback()
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    connectivityManager.registerNetworkCallback(request, networkCallback)
    awaitClose {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

fun ConnectivityManager.currentNetworkState(): NetworkState {
    val capabilities = activeNetwork?.let { getNetworkCapabilities(it) }
    return if (capabilities != null &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    ) {
        NetworkState.Available
    } else {
        NetworkState.Unavailable
    }
}

@Composable
fun rememberNetworkState(): NetworkState {
    val context = LocalContext.current
    return remember(context) {
        context.networkState().distinctUntilChanged()
    }.collectAsStateWithLifecycle(initialValue = NetworkState.Available).value
}

private fun ProducerScope<NetworkState>.networkCallback(): ConnectivityManager.NetworkCallback =
    object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(NetworkState.Available)
        }

        override fun onLost(network: Network) {
            trySend(NetworkState.Unavailable)
        }

        override fun onUnavailable() {
            trySend(NetworkState.Unavailable)
        }
    }

sealed class NetworkState {
    data object Available : NetworkState()
    data object Unavailable : NetworkState()
}

@Composable
fun NoNetworkBanner(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(color = MaterialTheme.colorScheme.errorContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 4.dp, bottom = 16.dp),
            imageVector = Icons.Rounded.Error,
            tint = MaterialTheme.colorScheme.onErrorContainer,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.labelLarge,
            text = stringResource(R.string.error_no_network),
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}
