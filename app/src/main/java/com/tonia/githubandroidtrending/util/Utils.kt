package com.tonia.githubandroidtrending.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tonia.githubandroidtrending.GlideApp
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

fun Any.logE(message: String, throwable: Throwable) { Log.e(this.javaClass.simpleName, message, throwable) }

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
        .into(imageView)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

//
//inline fun CompositeDisposable.callAndComposite(disposable: () -> Disposable) {
//    this.add(disposable())
//}
