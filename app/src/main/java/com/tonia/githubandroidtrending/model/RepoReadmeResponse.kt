package com.tonia.githubandroidtrending.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoReadmeResponse (
    val type: String,
    val encoding: String,
    val size: Int,
    val name: String,
    val path: String,
    val content: String,
    val html_url: String,
    val download_url: String
) : Parcelable
