package com.commondnd.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.browser.auth.AuthTabIntent
import com.commondnd.ui.web.getTrustedCustomTabsEnabledBrowserPackages
import com.commondnd.ui.web.tryLaunchAnyAuthTab
import com.commondnd.ui.web.tryLaunchAnyCustomTabs
import com.commondnd.ui.web.tryLaunchDefaultBrowser

fun LoginController.performCodeOauth(
    context: Context,
    authTabLauncher: ActivityResultLauncher<Intent>,
    authorizationUri: Uri,
    redirectUri: Uri,
    codeVerifier: String
) {
    val (defaultBrowser, trustedBrowserPackages) =
        getTrustedCustomTabsEnabledBrowserPackages(context)

    val defaultBrowserLaunched = defaultBrowser?.let {
        context.tryLaunchDefaultBrowser(
            authTabLauncher = authTabLauncher,
            defaultBrowser = it,
            authorizationUri = authorizationUri,
            redirectUri = redirectUri
        )
    } ?: false

    val anyAuthTabLaunched = if (!defaultBrowserLaunched) {
        authTabLauncher.tryLaunchAnyAuthTab(
            context = context,
            trustedBrowserPackages = trustedBrowserPackages,
            authorizationUri = authorizationUri,
            redirectUri = redirectUri
        )
    } else false

    val anyCustomTabLaunched = if (!defaultBrowserLaunched && !anyAuthTabLaunched) {
        context.tryLaunchAnyCustomTabs(
            trustedBrowserPackages = trustedBrowserPackages,
            authorizationUri = authorizationUri
        )
    } else false

    if (defaultBrowserLaunched || anyAuthTabLaunched || anyCustomTabLaunched) {
        startAuth(codeVerifier, redirectUri)
    } else {
        Toast.makeText(
            context,
            R.string.error_browser_not_found,
            Toast.LENGTH_SHORT
        ).show()
        finishAuth(null, AuthResult.VerificationFailed)
    }
}

fun LoginController.handleAuthResult(authResult: AuthTabIntent.AuthResult) {
    finishAuth(
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

fun LoginController.handleDeepLinkCallback(intent: Intent) {
    val uri = intent.data
    if (uri == null) {
        return
    }

    if (uri.scheme == "commondndoauth" && uri.host == "callback") {
        val code = uri.getQueryParameter("code")
        val error = uri.getQueryParameter("error")
        val authResult = when {
            code != null -> AuthResult.Success
            error == "access_denied" -> AuthResult.Cancelled
            error != null -> AuthResult.VerificationFailed
            else -> AuthResult.Unknown
        }

        finishAuth(code = code, authResult = authResult)
    }
}
