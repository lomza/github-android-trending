package com.tonia.githubandroidtrending.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * General top-level methods, used all across the project.
 */
fun Context.isInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected.let { it } ?: false
}
