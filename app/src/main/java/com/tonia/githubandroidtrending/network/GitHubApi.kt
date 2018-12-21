package com.tonia.githubandroidtrending.network

import com.tonia.githubandroidtrending.model.RepoReadmeResponse
import com.tonia.githubandroidtrending.model.TrendingAndroidReposResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface, which contains the list of GitHub API calls.
 */
interface GitHubApi {
    @GET("search/repositories?q=android&sort=stars&order=desc&per_page=10")
    fun getTrendingAndroidRepos(@Query("page") pageToFetch: Int): Single<TrendingAndroidReposResponse>

    @GET("/repos/{owner}/{repo}/readme")
    fun getRepoReadme(@Path("owner") owner: String, @Path("repo") repo: String): Single<RepoReadmeResponse>
}
