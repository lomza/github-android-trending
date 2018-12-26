package com.tonia.githubandroidtrending.repolist

import androidx.annotation.StringRes
import com.tonia.githubandroidtrending.BasePresenter
import com.tonia.githubandroidtrending.model.Repo

/**
 * MVP contract for Repo List fragment.
 */
class RepoListContract {
    interface Presenter : BasePresenter<View> {
        fun loadRepos(refresh: Boolean = false)
        fun loadMoreRepos(itemCount: Int, lastVisiblePosition: Int)
        fun selectRepo(repo: Repo)
    }

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun showErrorMessage(@StringRes messageResId: Int)
        fun setTitle(title: String)
        fun addRepos(repos: List<Repo>)
        fun refreshRepos(repos: List<Repo>)
        fun repoSelected(repo: Repo)
    }
}
