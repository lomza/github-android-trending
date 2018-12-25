package com.tonia.githubandroidtrending.repodetails

import android.content.Intent
import android.net.Uri
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.network.GitHubService
import com.tonia.githubandroidtrending.util.EspressoIdlingResource
import com.tonia.githubandroidtrending.util.networkCall
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class RepoDetailsPresenter : RepoDetailsContract.Presenter {

    private var view: RepoDetailsContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun loadRepo(repo: Repo?) {
        repo?.let {
            with(repo) {
                view?.setTitle(name)
                view?.showRepo(this)
            }
        }
    }

    override fun loadRepoReadme(owner: String, repo: String) {
        EspressoIdlingResource.increment()
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            networkCall(
                onSuccess = {
                    GitHubService.getRepoReadme(owner, repo)
                        .doOnSubscribe { view?.showProgressBar() }
                        .subscribeBy(
                            onError = {
                                view?.hideProgressBar()
                                view?.showErrorMessage(R.string.general_error_message)
                            },
                            onSuccess = { response ->
                                view?.showRepoReadme(response.download_url)
                                if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                                    EspressoIdlingResource.decrement()
                                }
                            }
                        )
                },
                onError = { isConnectionError ->
                    view?.hideProgressBar()

                    if (isConnectionError) {
                        view?.showErrorMessage(R.string.internet_connection_error_message)
                    } else {
                        view?.showErrorMessage(R.string.general_error_message)
                    }
                })
        )
    }

    override fun shareRepo(url: String, message: String, chooserTitle: String) {
        if (url.isNotBlank()) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            view?.showRepoShareChooser(Intent.createChooser(sendIntent, chooserTitle))
        }
    }

    override fun openGitHubProject(url: String) {
        if (url.isNotBlank()) {
            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            view?.showProjectInBrowser(intent)
        }
    }

    override fun attach(view: RepoDetailsContract.View) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detach() {
        this.view = null
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
