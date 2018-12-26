package com.tonia.githubandroidtrending

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable

/**
 * Base view to be implemented by all fragments.
 */
interface BaseView {

    @LayoutRes
    fun getContentResource(): Int

    fun init(view: View, @Nullable state: Bundle?)
}
