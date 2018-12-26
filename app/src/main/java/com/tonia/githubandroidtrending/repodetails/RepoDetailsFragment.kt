package com.tonia.githubandroidtrending.repodetails

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.util.*
import es.dmoral.markdownview.MarkdownView
import kotlinx.android.synthetic.main.fragment_repo_details.view.*
import kotlinx.android.synthetic.main.fragment_repo_list.*

/**
 * Fragment, that shows the details of a chosen GitHub repo.
 * Contains the presenter and refreshes the UI on presenter's callbacks.
 */
class RepoDetailsFragment : BaseFragment(), RepoDetailsContract.View {

    private var parentView: View? = null
    private val presenter = RepoDetailsPresenter()

    companion object {
        const val TAG = "repo_details_fragment"
        private const val EXTRA_REPO = "extra_repo"

        fun newInstance(@NonNull repo: Repo) =
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
        setHasOptionsMenu(true)
    }

    override fun getContentResource() = R.layout.fragment_repo_details

    override fun init(view: View, state: Bundle?) {
        parentView = view
    }

    override fun showProgressBar() {
        parentView?.progressBarDetails?.visible()
    }

    override fun hideProgressBar() {
        parentView?.progressBarDetails?.gone()
    }

    override fun showErrorMessage(messageResId: Int) {
        context?.toastLong(getString(messageResId))
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun showRepo(repo: Repo) {
        parentView?.apply {
            with(repo) {
                loadImageFromUrl(
                    fragment = this@RepoDetailsFragment,
                    url = owner.avatar_url,
                    imageView = imageViewCollapsing
                )

                textViewStars.text = stargazers_count.toString()
                textViewForks.text = forks_count.toString()
                textViewWatchers.text = watchers_count.toString()
                textViewOpenIssues.text = open_issues_count.toString()
                textViewLanguage.text =  language.valueOrUnknown()
                textViewLicense.text = license?.name.valueOrUnknown()
                textViewLastUpdated.text = getRepoDate(null)
                textViewFullName.text = full_name
                textViewDesc.text = description
                imageButtonGitHubRepo.setOnClickListener {
                    presenter.openGitHubProject(html_url)
                }

                fabShare.setOnClickListener {
                    presenter.shareRepo(
                        url = html_url,
                        message = getString(R.string.repo_share_text, html_url),
                        chooserTitle = getString(R.string.send_to))
                }

                presenter.loadRepoReadme(owner.login, name)
            }
        }
    }

    override fun showRepoReadme(url: String) {
        parentView?.markdownViewReadmeContent?.loadFromUrl(url)
        parentView?.markdownViewReadmeContent?.setOnMarkdownRenderingListener(object :
            MarkdownView.OnMarkdownRenderingListener {
            override fun onMarkdownRenderError() {
                hideProgressBar()
                showErrorMessage(R.string.general_error_message)
            }

            override fun onMarkdownFinishedRendering() {
                hideProgressBar()
            }
        })
    }

    override fun showRepoShareChooser(intent: Intent) {
        context?.startActivity(intent)
    }

    override fun showProjectInBrowser(intent: Intent) {
        context?.startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

        presenter.attach(this)
        arguments?.let {
            presenter.loadRepo(it.getParcelable(EXTRA_REPO))
        }
    }

    override fun onStop() {
        super.onStop()

        presenter.detach()
    }
}
