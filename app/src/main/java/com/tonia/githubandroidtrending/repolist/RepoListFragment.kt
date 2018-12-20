package com.tonia.githubandroidtrending.repolist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.network.GitHubService
import com.tonia.githubandroidtrending.repodetails.RepoDetailsFragment
import com.tonia.githubandroidtrending.util.addFragment
import com.tonia.githubandroidtrending.util.logE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*

class RepoListFragment : BaseFragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    companion object {
        const val TAG = "repo_list_fragment"

        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }

    override fun getContentResource() = R.layout.fragment_repo_list

    override fun init(view: View, state: Bundle?) {

        (view.context as AppCompatActivity).setSupportActionBar(toolbar)
        view.recyclerViewRepoList.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        populateRepoList(view)
    }

    private fun populateRepoList(view: View) {
        compositeDisposable.add(GitHubService.getTrendingRepos(1)
            .subscribeBy(
                onSuccess = { repos ->
                    view.recyclerViewRepoList.adapter = RepoListAdapter(repos) {
                        (view.context as AppCompatActivity).addFragment(
                            RepoDetailsFragment.newInstance(it), R.id.container, RepoDetailsFragment.TAG)
                    }
                },
                onError = {
                    logE(it.message ?: "Error getting repo list.", it)
                }
            ))


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.title = getString(R.string.app_name)
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
