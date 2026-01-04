package com.commondnd.ui.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.commondnd.data.user.NoAccountException
import com.commondnd.ui.core.ErrorScreen
import com.commondnd.ui.core.ErrorSpec
import com.commondnd.ui.core.ErrorSpecProvider

@Composable
fun LoginErrorScreen(
    modifier: Modifier = Modifier,
    error: Throwable,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    val context = LocalContext.current
    ErrorScreen(
        modifier = modifier,
        error = error,
        errorSpecProvider = rememberLoginErrorSpecProvider(context),
        onBack = onBack,
        onAction = {
            when (it) {
                ACTION_KEY_RETRY -> onRetry()
                ACTION_KEY_BACK -> onBack()
            }
        }
    )
}

@Composable
private fun rememberLoginErrorSpecProvider(context: Context): ErrorSpecProvider =
    remember(context) {
        object : ErrorSpecProvider {
            override fun get(error: Throwable): ErrorSpec? {
                return when (error) {
                    is NoAccountException -> ErrorSpec(
                        title = context.getString(R.string.title_error_no_player_account),
                        description = context.getString(R.string.description_error_no_player_account),
                        primaryAction = ACTION_KEY_BACK to context.getString(com.commondnd.ui.core.R.string.label_action_back)
                    )

                    else -> ErrorSpec(
                        title = context.getString(com.commondnd.ui.core.R.string.title_error_generic),
                        primaryAction = ACTION_KEY_RETRY to context.getString(com.commondnd.ui.core.R.string.label_action_retry)
                    )
                }
            }
        }
    }

private const val ACTION_KEY_BACK = "action_back"
private const val ACTION_KEY_RETRY = "action_retry"
