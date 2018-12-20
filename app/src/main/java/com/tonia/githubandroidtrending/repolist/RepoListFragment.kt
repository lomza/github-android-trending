package com.tonia.githubandroidtrending.repolist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.repodetails.RepoDetailsFragment
import com.tonia.githubandroidtrending.util.addFragment
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_repo_list.view.*

class RepoListFragment : BaseFragment() {

    companion object {
        const val TAG = "repo_list_fragment"

        fun newInstance(): RepoListFragment {
            return RepoListFragment()
        }
    }

    override fun getContentResource() = R.layout.fragment_repo_list

    override fun init(view: View, state: Bundle?) {
        // TODO remove
        val repos = arrayListOf<Repo>()

        view.recyclerViewRepoList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        view.recyclerViewRepoList.adapter = RepoListAdapter(repos) {
            // TODO
            (view.context as AppCompatActivity).addFragment(RepoDetailsFragment(), R.id.container, RepoDetailsFragment.TAG)
        }

        (view.context as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.title = getString(R.string.app_name)
    }
}
