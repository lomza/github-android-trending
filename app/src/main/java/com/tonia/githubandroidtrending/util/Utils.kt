package com.tonia.githubandroidtrending.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.tonia.githubandroidtrending.GlideApp
import com.tonia.githubandroidtrending.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/**
 * General extension functions, top-level variables and functions, used all across the project.
 */

fun Any.logD(message: String) { Log.d(this.javaClass.simpleName, message) }

fun Any.logI(message: String) { Log.i(this.javaClass.simpleName, message) }

fun Any.logW(message: String) { Log.w(this.javaClass.simpleName, message) }

fun Any.logE(message: String) { Log.e(this.javaClass.simpleName, message) }

fun Any.logE(message: String, throwable: Throwable) { Log.e(this.javaClass.simpleName, message, throwable) }

fun Context.toastShort(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

inline fun FragmentManager.transaction(call: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().call().commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, resId: Int) {
    supportFragmentManager.transaction { replace(resId, fragment) }
}

fun AppCompatActivity.addFragment(fragment: Fragment, resId: Int, tag: String) {
    supportFragmentManager.transaction { add(resId, fragment).addToBackStack(tag) }
}

fun <T> getSchedulersForSingleNetworkCall(): (Single<T>) -> Single<T> {
    return {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun loadImageFromUrl(fragment: Fragment, url: String, imageView: ImageView) {
    GlideApp
        .with(fragment)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_github_octocat)
        .into(imageView)
}

val repoDateInputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
val repoDateOutputFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

fun networkCall(onSuccess: () -> Unit, onError: (isConnectivityError: Boolean) -> Unit) = ReactiveNetwork.checkInternetConnectivity()
    .compose(getSchedulersForSingleNetworkCall())
    .subscribeBy(
        onSuccess = { isConnectivity ->
            if (isConnectivity) onSuccess() else onError(true)
        },
        onError = {
            onError(false)
        }
    )
