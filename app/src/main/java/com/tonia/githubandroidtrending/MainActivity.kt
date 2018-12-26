package com.tonia.githubandroidtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.repodetails.RepoDetailsFragment
import com.tonia.githubandroidtrending.repolist.RepoListFragment
import com.tonia.githubandroidtrending.util.addFragment
import com.tonia.githubandroidtrending.util.replaceFragment

/**
 * Main (and single) activity for this app.
 * Contains and manages all the fragments in it.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRepoList()
    }

    private fun showRepoList() {
        replaceFragment(RepoListFragment.newInstance(), R.id.container)
    }

    fun showRepoDetails(repo: Repo) {
        addFragment(RepoDetailsFragment.newInstance(repo), R.id.container, RepoDetailsFragment.TAG)
    }
}
