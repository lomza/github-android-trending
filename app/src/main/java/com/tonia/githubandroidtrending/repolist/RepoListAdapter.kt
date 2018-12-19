package com.tonia.githubandroidtrending.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_repo_list_item.*

class RepoListAdapter(private val repos: ArrayList<Repo>,
                      private val listener: (Repo) -> Unit) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.view_repo_list_item, parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) = holder.bind(repos[position], listener)

    override fun getItemCount() = repos.size

    class RepoViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(repo: Repo, listener: (Repo) -> Unit) {
            textViewTitle.text = repo.title
            textViewDesc.text = repo.desc
            textViewLanguage.text = repo.language
            textViewStars.text = repo.stars.toString()
            textViewForks.text = repo.forks.toString()

            containerView.setOnClickListener { listener(repo) }
        }
    }
}