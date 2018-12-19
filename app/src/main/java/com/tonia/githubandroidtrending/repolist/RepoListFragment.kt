package com.tonia.githubandroidtrending.repolist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
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
        repos.add(Repo("octocat/Android", "Great Android project", "Kotlin", 43, 11))
        repos.add(Repo("gc/Lib-Android", "Great Android project", "Java", 25, 9))
        repos.add(Repo("abc/Kotlin-And", "Great Android project", "Kotlin", 199, 58))

        view.recyclerViewRepoList.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        view.recyclerViewRepoList.adapter = RepoListAdapter(repos) {
            // TODO
        }
    }
}
