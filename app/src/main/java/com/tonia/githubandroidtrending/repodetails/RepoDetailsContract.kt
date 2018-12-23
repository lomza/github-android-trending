package com.tonia.githubandroidtrending.repodetails

import android.content.Intent
import androidx.annotation.StringRes
import com.tonia.githubandroidtrending.BasePresenter
import com.tonia.githubandroidtrending.model.Repo

class RepoDetailsContract {
    interface Presenter : BasePresenter<View> {
        fun loadRepo(repo: Repo?)
        fun loadRepoReadme(owner: String, repo: String)
        fun openGitHubProject(url: String)
        fun shareRepo(url: String, message: String, chooserTitle: String)
    }

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun showErrorMessage(@StringRes messageResId: Int)
        fun setTitle(title: String)
        fun showRepo(repo: Repo)
        fun showRepoReadme(url: String)
        fun showProjectInBrowser(intent: Intent)
        fun showRepoShareChooser(intent: Intent)
    }
}
