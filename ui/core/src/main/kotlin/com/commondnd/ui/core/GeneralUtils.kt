package com.commondnd.ui.core

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

fun Context.getVersionName(): String {
    return try {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, 0)
        }
        val versionName = packageInfo.versionName ?: "Unknown"
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()
        }
        "$versionName ($versionCode)"
    } catch (_: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}
