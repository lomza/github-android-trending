package com.tonia.githubandroidtrending.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import kotlinx.android.synthetic.main.fragment_repo_details.view.*

class RepoDetailsFragment : BaseFragment() {

    private var repo: Repo? = null

    companion object {
        const val TAG = "repo_details_fragment"
        private const val EXTRA_REPO = "extra_repo"

        fun newInstance(repo: Repo) =
            RepoDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_REPO, repo)
                }
            }
    }

    override fun getContentResource() = R.layout.fragment_repo_details

    override fun init(view: View, state: Bundle?) {
        arguments?.let {
            repo = it.getParcelable(EXTRA_REPO)

            repo?.let { repo ->
                with(repo) {
                    view.textViewStars.text = stargazers_count.toString()
                    view.textViewForks.text = forks_count.toString()
                    view.textViewWatchers.text = watchers_count.toString()
                    view.textViewOpenIssues.text = open_issues_count.toString()
                    view.textViewLanguage.text = language
                    view.textViewLicense.text = license?.name
                    view.textViewLastUpdated.text = updated_at
                    view.textViewDesc.text = description
                    view.imageButtonGitHubRepo.setOnClickListener {
                        if (html_url.isNotEmpty()) {
                            showGitHubProjectInBrowser(html_url)
                        }
                    }
                }
            }
        }
    }

    private fun showGitHubProjectInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
    }
}
