package com.tonia.githubandroidtrending.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * General top-level methods, used all across the project.
 */

fun Context.isInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnected.let { it } ?: false
}

fun Any.logD(message: String) { Log.d(this.javaClass.simpleName, message) }

fun Any.logI(message: String) { Log.i(this.javaClass.simpleName, message) }

fun Any.logW(message: String) { Log.w(this.javaClass.simpleName, message) }

fun Any.logE(message: String) { Log.e(this.javaClass.simpleName, message) }

inline fun FragmentManager.transaction(call: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().call().commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, resId: Int) {
    supportFragmentManager.transaction { replace(resId, fragment) }
}
