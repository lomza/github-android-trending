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
import com.tonia.githubandroidtrending.network.GitHubService.getRepoReadme
import com.tonia.githubandroidtrending.util.*
import es.dmoral.markdownview.MarkdownView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_repo_details.view.*
import kotlinx.android.synthetic.main.fragment_repo_list.*

class RepoDetailsFragment : BaseFragment() {

    private var repo: Repo? = null
    private var compositeDisposable = CompositeDisposable()

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
                    loadImageFromUrl(
                        fragment = this@RepoDetailsFragment,
                        url = owner.avatar_url,
                        imageView = view.imageViewCollapsing
                    )

                    view.textViewStars.text = stargazers_count.toString()
                    view.textViewForks.text = forks_count.toString()
                    view.textViewWatchers.text = watchers_count.toString()
                    view.textViewOpenIssues.text = open_issues_count.toString()
                    view.textViewLanguage.text = if (language?.isNotEmpty() != true) language else "?"
                    view.textViewLicense.text = if (license?.name?.isNotEmpty() != true) license?.name else "?"
                    view.textViewLastUpdated.text =
                            repoDateOutputFormatter.format(repoDateInputFormatter.parse(updated_at))
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


                    compositeDisposable = CompositeDisposable()
                    compositeDisposable.add(
                        networkCall(
                            onSuccess = {
                                // Get repo's README content
                                getRepoReadme(owner.login, name)
                                    .doOnSubscribe { view.progressBarDetails.visible() }
                                    .subscribeBy(
                                        onError = {
                                            view.progressBarDetails.gone()
                                            context?.toastLong(getString(R.string.general_error_message))
                                        },
                                        onSuccess = {response ->
                                            view.markdownViewReadmeContent.loadFromUrl(response.download_url)
                                            view.markdownViewReadmeContent.setOnMarkdownRenderingListener(object :
                                                MarkdownView.OnMarkdownRenderingListener {
                                                override fun onMarkdownRenderError() {
                                                    view.progressBarDetails.gone()
                                                    context?.toastLong(getString(R.string.general_error_message))
                                                }

                                                override fun onMarkdownFinishedRendering() {
                                                    view.progressBarDetails.gone()
                                                }
                                            })
                                        }
                                    )
                            },
                            onError = { isConnectionError ->
                                view.progressBarDetails.gone()

                                if (isConnectionError) {
                                    context?.toastLong(getString(R.string.internet_connection_error_message))
                                } else {
                                    context?.toastLong(getString(R.string.general_error_message))
                                }
                            })
                    )
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
