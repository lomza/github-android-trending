package com.tonia.githubandroidtrending.repodetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.util.loadImageFromUrl
import com.tonia.githubandroidtrending.util.repoDateInputFormatter
import com.tonia.githubandroidtrending.util.repoDateOutputFormatter
import kotlinx.android.synthetic.main.fragment_repo_details.view.*
import kotlinx.android.synthetic.main.fragment_repo_list.*

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (context as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = repo?.name
        setHasOptionsMenu(true)
    }

    override fun getContentResource() = R.layout.fragment_repo_details

    override fun init(view: View, state: Bundle?) {
        arguments?.let {
            repo = it.getParcelable(EXTRA_REPO)

            repo?.let { repo ->
                with(repo) {
                    loadImageFromUrl(fragment = this@RepoDetailsFragment, url = owner.avatar_url, imageView = view.imageViewCollapsing)

                    view.textViewStars.text = stargazers_count.toString()
                    view.textViewForks.text = forks_count.toString()
                    view.textViewWatchers.text = watchers_count.toString()
                    view.textViewOpenIssues.text = open_issues_count.toString()
                    view.textViewLanguage.text = if (language?.isNotEmpty() != true) language else "?"
                    view.textViewLicense.text = if (license?.name?.isNotEmpty() != true) license?.name else "?"
                    view.textViewLastUpdated.text = repoDateOutputFormatter.format(repoDateInputFormatter.parse(updated_at))
                    view.textViewFullName.text = full_name
                    view.textViewDesc.text = description
                    view.imageButtonGitHubRepo.setOnClickListener {
                        if (html_url.isNotEmpty()) {
                            showGitHubProjectInBrowser(html_url)
                        }
                    }

                    view.fabShare.setOnClickListener {
                        shareRepo(html_url)
                    }
                }
            }
        }
    }

    private fun showGitHubProjectInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun shareRepo(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.repo_share_text, url))
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.send_to)))
    }
}
