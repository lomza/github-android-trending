package com.tonia.githubandroidtrending.repolist

import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.network.GitHubService
import com.tonia.githubandroidtrending.util.logE
import com.tonia.githubandroidtrending.util.networkCall
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class RepoListPresenter : RepoListContract.Presenter {
    private var view: RepoListContract.View? = null
    private var compositeDisposable = CompositeDisposable()
    private var currentPage = 1
    private var isLoading = false

    override fun loadRepos(refresh: Boolean) {
        if (!isLoading) {
            isLoading = true
            compositeDisposable.add(
                networkCall(
                    onSuccess = {
                        // Get and set repos list
                        GitHubService.getTrendingRepos(if (refresh) 1 else currentPage)
                            .doOnSubscribe { view?.showProgressBar() }
                            .doFinally {
                                isLoading = false
                                view?.hideProgressBar()
                            }
                            .subscribeBy(

                                onSuccess = { repos ->
                                    if (refresh) {
                                        view?.refreshRepos(repos)
                                    } else {
                                        view?.addRepos(repos)
                                    }
                                },
                                onError = {
                                    logE(it.message ?: "Error getting repo list.", it)
                                    view?.showErrorMessage(R.string.general_error_message)
                                }
                            )
                    },
                    onError = { isConnectionError ->
                        isLoading = false
                        view?.hideProgressBar()

                        if (isConnectionError) {
                            view?.showErrorMessage(R.string.internet_connection_error_message)
                        } else {
                            view?.showErrorMessage(R.string.general_error_message)
                        }
                    })
            )
        }
    }

    override fun loadMoreRepos(itemCount: Int, lastVisiblePosition: Int) {
        if (!isLoading && itemCount <= (lastVisiblePosition + 1)) {
            currentPage++
            loadRepos()
        }
    }

    override fun selectRepo(repo: Repo) {
        view?.repoSelected(repo)
    }

    override fun attach(view: RepoListContract.View) {
        this.view = view
        compositeDisposable = CompositeDisposable()
        loadRepos(refresh = true)
    }

    override fun detach() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        this.view = null
    }
}
