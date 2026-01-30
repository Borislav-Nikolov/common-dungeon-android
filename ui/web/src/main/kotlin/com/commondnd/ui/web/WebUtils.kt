package com.commondnd.ui.web

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ResolveInfoFlags
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.browser.auth.AuthTabIntent
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService

val TrustedCustomTabsBrowserPackages = listOf(
    // Chrome
    "com.android.chrome",
    "com.chrome.beta",
    "com.chrome.dev",
    "com.chrome.canary",
    // Firefox
    "org.mozilla.firefox",
    "org.mozilla.firefox_beta",
    "org.mozilla.fenix",
    // Samsung
    "com.sec.android.app.sbrowser",
    "com.sec.android.app.sbrowser.beta",
    // Brave
    "com.brave.browser",
    "com.brave.browser_beta",
    "com.brave.browser_nightly",
    // Microsoft Edge
    "com.microsoft.emmx",
    // Vivaldi
    "com.vivaldi.browser",
    "com.vivaldi.browser.snapshot"
)

/**
 * Get the browser app packages that support CustomTabs and are included in the [TrustedCustomTabsBrowserPackages].
 *
 * @return a [List] of packages, ordered by the following priority:
 *   * first is the default browser app if it supports CustomTabs;
 *   * then all other packages ordered by the [TrustedCustomTabsBrowserPackages].
 */
fun getTrustedCustomTabsEnabledBrowserPackages(context: Context): Pair<String?, List<String>> {

    val allCustomTabsEnabledPackages = getAvailableCustomTabsEnabledBrowserPackages(context)

    val defaultBrowserPackage = getDefaultBrowserPackage(context).takeIf {
        val inCustomTabs = it in allCustomTabsEnabledPackages
        val inTrusted = it in TrustedCustomTabsBrowserPackages
        inCustomTabs && inTrusted
    }

    return defaultBrowserPackage to buildList {
        TrustedCustomTabsBrowserPackages.asSequence().filter { it in allCustomTabsEnabledPackages }
            .forEach {
                if (defaultBrowserPackage != it) {
                    add(it)
                }
            }
    }
}

/**
 * Get the packages of all browsers apps available on the device that support CustomTabs.
 *
 * @return a [Set] of packages.
 */
fun getAvailableCustomTabsEnabledBrowserPackages(context: Context): Set<String> {

    val customTabsServiceIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)

    val customTabsInfos = context.packageManager.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            queryIntentServices(customTabsServiceIntent, ResolveInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            queryIntentServices(customTabsServiceIntent, 0)
        }
    }


    val result = if (customTabsInfos.isEmpty()) emptySet() else customTabsInfos
        .asSequence()
        .mapNotNull { it.serviceInfo?.packageName }
        .toSet()

    return result
}

/**
 * Get the package of the default browser app on the device.
 *
 * @return the package as [String] or `null` if no browser was found.
 */
fun getDefaultBrowserPackage(context: Context): String? {
    val browserIntent = Intent()
        .setAction(Intent.ACTION_VIEW)
        .addCategory(Intent.CATEGORY_BROWSABLE)
        .setData(Uri.fromParts("http", "", null))
    val defaultBrowserInfos = context.packageManager.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            queryIntentActivities(
                browserIntent,
                ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
        }
    }
    return if (defaultBrowserInfos.isEmpty()) null else defaultBrowserInfos.singleOrNull()?.activityInfo?.packageName
}

fun String.isAuthTabEnabledPackage(context: Context): Boolean {
    return CustomTabsClient.isAuthTabSupported(context, this)
}

fun Context.tryLaunchDefaultBrowser(
    authTabLauncher: ActivityResultLauncher<Intent>,
    defaultBrowser: String,
    authorizationUri: Uri,
    redirectUri: Uri
): Boolean {
    if (authTabLauncher.tryLaunchAuthTab(this, defaultBrowser, authorizationUri, redirectUri)) {
        return true
    }
    return tryLaunchCustomTabs(defaultBrowser, authorizationUri)
}

fun ActivityResultLauncher<Intent>.tryLaunchAnyAuthTab(
    context: Context,
    trustedBrowserPackages: List<String>,
    authorizationUri: Uri,
    redirectUri: Uri
): Boolean {

    if (trustedBrowserPackages.isEmpty()) {
        return false
    }

    for (browserPackage in trustedBrowserPackages) {
        if (tryLaunchAuthTab(context, browserPackage, authorizationUri, redirectUri)) {
            return true
        }
    }

    return false
}

fun ActivityResultLauncher<Intent>.tryLaunchAuthTab(
    context: Context,
    browserPackage: String,
    authorizationUri: Uri,
    redirectUri: Uri
): Boolean {
    if (!browserPackage.isAuthTabEnabledPackage(context = context)) {
        return false
    }
    try {
        AuthTabIntent.Builder().build().run {
            intent.setPackage(browserPackage)
            launch(this@tryLaunchAuthTab, authorizationUri, redirectUri.scheme!!)
        }
        return true
    } catch (_: ActivityNotFoundException) {
        return false
    }
}

fun Context.tryLaunchAnyCustomTabs(
    trustedBrowserPackages: List<String>,
    authorizationUri: Uri
): Boolean {

    if (trustedBrowserPackages.isEmpty()) {
        return false
    }

    for (browserPackage in trustedBrowserPackages) {
        if (tryLaunchCustomTabs(browserPackage, authorizationUri)) {
            return true
        }
    }

    return false
}

fun Context.tryLaunchCustomTabs(
    browserPackage: String,
    authorizationUri: Uri
): Boolean {
    try {
        openInCustomTab(browserPackage, authorizationUri)
        return true
    } catch (_: ActivityNotFoundException) {
        return false
    }
}

fun Context.openInCustomTab(
    packageName: String,
    uri: Uri
) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setUrlBarHidingEnabled(true)
        .setShowTitle(true)
        .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
        .setBookmarksButtonEnabled(false)
        .setDownloadButtonEnabled(false)
        .build()
    customTabsIntent.intent.setPackage(packageName)
    customTabsIntent.launchUrl(this, uri)
}
