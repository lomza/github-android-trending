package com.tonia.githubandroidtrending.model

data class Repo(val title: String,
                val desc: String,
                val language: String,
                val stars: Int,
                val forks: Int)
