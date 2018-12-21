package com.tonia.githubandroidtrending.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_repo_list_item.*

class RepoListAdapter(private var repos: MutableList<Repo>,
                      private val listener: (Repo) -> Unit) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.view_repo_list_item, parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) = holder.bind(repos[position], listener)

    override fun getItemCount() = repos.size

    fun refreshRepos(repos: List<Repo>) {
        this.repos = repos.toMutableList()
    }

    fun addRepos(repos: List<Repo>) {
        this.repos.addAll(repos)
    }

    class RepoViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(repo: Repo, listener: (Repo) -> Unit) {
            with(repo) {
                textViewTitle.text = full_name
                textViewDesc.text = description
                textViewLanguage.text = if (language?.isNotEmpty() != null) language else "?"
                textViewStars.text = stargazers_count.toString()
                textViewForks.text = forks_count.toString()

                containerView.setOnClickListener { listener(this) }
            }
        }
    }
}
