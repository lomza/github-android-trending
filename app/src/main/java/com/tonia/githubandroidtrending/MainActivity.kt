package com.tonia.githubandroidtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tonia.githubandroidtrending.repolist.RepoListFragment
import com.tonia.githubandroidtrending.util.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        replaceFragment(RepoListFragment(), R.id.container)
    }

    private fun showRepoDetailsFragment() {
        // TODO
    }
}
