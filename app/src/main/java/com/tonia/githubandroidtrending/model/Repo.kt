package com.tonia.githubandroidtrending.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val id: String,
    val name: String,
    val full_name: String,
    val html_url: String,
    val description: String,
    val language: String,
    val stargazers_count: Int,
    val forks_count: Int,
    val watchers_count: Int,
    val open_issues_count: Int,
    val updated_at: String,
    val owner: Owner,
    val license: License?
) : Parcelable
