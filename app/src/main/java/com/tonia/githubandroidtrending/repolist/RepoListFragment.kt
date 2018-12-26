package com.tonia.githubandroidtrending.repolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.MainActivity
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.util.gone
import com.tonia.githubandroidtrending.util.toastLong
import com.tonia.githubandroidtrending.util.visible
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*

/**
 * Fragment, that shows the list of GitHub repositories.
 * Contains the presenter and refreshes the UI on presenter's callbacks.
 */
class RepoListFragment : BaseFragment(), RepoListContract.View {

    private var parentView: View? = null
    private var listAdapter: RepoListAdapter? = null
    private val presenter = RepoListPresenter()

    companion object {
        const val TAG = "repo_list_fragment"

        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.attach(this)
        setListAdapter()
        setListOnScrollListener()
    }

    override fun onStop() {
        super.onStop()

        presenter.detach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_repo_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                presenter.loadRepos(refresh = true)
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
    }

    private fun setListAdapter() {
        parentView?.recyclerViewRepoList?.layoutManager = LinearLayoutManager(parentView?.context, RecyclerView.VERTICAL, false)
        listAdapter = RepoListAdapter(mutableListOf()) {
            presenter.selectRepo(it)
        }

        parentView?.recyclerViewRepoList?.adapter = listAdapter
    }

    private fun setListOnScrollListener() {
        parentView?.recyclerViewRepoList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemCount = recyclerView.layoutManager?.itemCount ?: 0
                val lastVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                presenter.loadMoreRepos(itemCount, lastVisiblePosition)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (context as AppCompatActivity).setSupportActionBar(toolbar)
        setTitle(getString(R.string.app_name))
        setHasOptionsMenu(true)
    }

    override fun showProgressBar() {
        parentView?.progressBarList?.visible()
    }

    override fun hideProgressBar() {
        parentView?.progressBarList?.gone()
    }

    override fun showErrorMessage(@StringRes messageResId: Int) {
        context?.toastLong(getString(messageResId))
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun addRepos(repos: List<Repo>) {
        listAdapter?.addRepos(repos)
    }

    override fun refreshRepos(repos: List<Repo>) {
        listAdapter?.refreshRepos(repos)
        parentView?.recyclerViewRepoList?.scrollToPosition(0)
    }

    override fun repoSelected(repo: Repo) {
        (context as? MainActivity)?.showRepoDetails(repo)
    }
}
