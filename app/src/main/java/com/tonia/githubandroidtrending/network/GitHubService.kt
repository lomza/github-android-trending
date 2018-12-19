package com.tonia.githubandroidtrending.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * GitHub service object which acts like an entry point to GitHub API calls.
 */
object GitHubService {

    const val BASE_URL = "https://api.github.com"

    var retrofit: Retrofit? = null

    val client: Retrofit by lazy {
        retrofit ?:
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
