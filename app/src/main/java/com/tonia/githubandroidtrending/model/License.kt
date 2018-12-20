package com.tonia.githubandroidtrending.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class License(
    val key: String,
    val name: String,
    val url: String
) : Parcelable
