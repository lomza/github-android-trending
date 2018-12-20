package com.tonia.githubandroidtrending.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val url: String
) : Parcelable
