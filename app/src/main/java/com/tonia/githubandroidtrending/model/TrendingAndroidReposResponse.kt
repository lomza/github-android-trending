package com.tonia.githubandroidtrending.model

data class TrendingAndroidReposResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repo>
)
