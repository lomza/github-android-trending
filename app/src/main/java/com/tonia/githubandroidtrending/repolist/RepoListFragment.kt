package com.tonia.githubandroidtrending.repolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.network.GitHubService
import com.tonia.githubandroidtrending.repodetails.RepoDetailsFragment
import com.tonia.githubandroidtrending.util.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*

class RepoListFragment : BaseFragment() {

    private lateinit var parentView: View
    private lateinit var listAdapter: RepoListAdapter
    private var compositeDisposable = CompositeDisposable()
    private var currentPage = 1
    private var isLoading = true

    companion object {
        const val TAG = "repo_list_fragment"

        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_repo_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                populateRepoList(refresh = true)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun getContentResource() = R.layout.fragment_repo_list

    override fun init(view: View, state: Bundle?) {

        parentView = view

        parentView.recyclerViewRepoList.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        listAdapter = RepoListAdapter(mutableListOf()) {
            (view.context as AppCompatActivity).addFragment(
                RepoDetailsFragment.newInstance(it), R.id.container, RepoDetailsFragment.TAG
            )
        }

        parentView.recyclerViewRepoList.adapter = listAdapter

        parentView.recyclerViewRepoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemCount = recyclerView.layoutManager?.itemCount ?: 0
                val lastVisiblePosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                loadMore(itemCount, lastVisiblePosition)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable = CompositeDisposable()
        populateRepoList(refresh = true)

        //showGeneralErrorDialog().show((context as AppCompatActivity).supportFragmentManager, "HH")
    }

    private fun loadMore(itemCount: Int, lastVisiblePosition: Int) {
        if (!isLoading && itemCount <= (lastVisiblePosition + 1)) {
            currentPage++
            isLoading = true
            populateRepoList()
        }
    }

    private fun populateRepoList(refresh: Boolean = false) {
        compositeDisposable.add(
            networkCall(
                onSuccess = {
                    // Get and set repos list
                    GitHubService.getTrendingRepos(if (refresh) 1 else currentPage)
                        .doOnSubscribe { parentView.progressBarList.visible() }
                        .doFinally {
                            isLoading = false
                            parentView.progressBarList.gone()
                        }
                        .subscribeBy(
                            onSuccess = { repos ->
                                if (refresh) {
                                    listAdapter.refreshRepos(repos)
                                    parentView.recyclerViewRepoList.scrollToPosition(0)
                                } else {
                                    listAdapter.addRepos(repos)
                                }
                                listAdapter.notifyDataSetChanged()
                            },
                            onError = {
                                logE(it.message ?: "Error getting repo list.", it)
                                showGeneralErrorDialog((parentView.context as AppCompatActivity).supportFragmentManager)
                            }
                        )
                },
                onError = { isConnectionError ->
                    isLoading = false
                    parentView.progressBarList.gone()

                    if (isConnectionError) {
                        showInternetNotAvailableDialog((parentView.context as AppCompatActivity).supportFragmentManager)
                    } else {
                        showGeneralErrorDialog((parentView.context as AppCompatActivity).supportFragmentManager)
                    }
                })
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (parentView.context as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)
        setHasOptionsMenu(true)
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}
