package com.tonia.githubandroidtrending

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable

interface BaseView {

    @LayoutRes
    fun getContentResource(): Int

    fun init(view: View, @Nullable state: Bundle?)
}
