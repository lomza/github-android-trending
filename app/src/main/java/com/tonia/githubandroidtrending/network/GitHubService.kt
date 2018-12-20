package com.tonia.githubandroidtrending.network

import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.util.getSchedulersForSingleNetworkCall
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * GitHub service object which acts like an entry point to GitHub API calls.
 */
object GitHubService {

    private const val BASE_URL = "https://api.github.com"

    private var retrofit: Retrofit? = null

    private val client: Retrofit by lazy {
        retrofit ?: Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val service: GitHubApi by lazy {
        client.create(GitHubApi::class.java)
    }

    fun getTrendingRepos(page: Int): Single<List<Repo>> =
        service.getTrendingAndroidRepos(page)
            .flatMap {
                return@flatMap Single.just(it.items)
            }
            .compose(getSchedulersForSingleNetworkCall())
}
