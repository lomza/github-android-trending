package com.tonia.githubandroidtrending.network

import com.tonia.githubandroidtrending.model.RepoDetailsResponse
import com.tonia.githubandroidtrending.model.TrendingAndroidReposResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface, which contains the list of GitHub API calls.
 */
interface GitHubApi {
    @GET("/repos/")
    fun getTrendingAndroidRepos(): Flowable<TrendingAndroidReposResponse>

    @GET("/repos/{owner}/{repo}")
    fun getRepoDetails(@Path("owner") owner: String, @Path("repo") repo: String): Flowable<RepoDetailsResponse>
}
