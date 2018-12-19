package com.tonia.githubandroidtrending.repodetails

import android.os.Bundle
import android.view.View
import com.tonia.githubandroidtrending.BaseFragment
import com.tonia.githubandroidtrending.R

class RepoDetailsFragment : BaseFragment() {

    companion object {
        const val TAG = "repo_details_fragment"

        fun newInstance(): RepoDetailsFragment {
            return RepoDetailsFragment()
        }
    }

    override fun getContentResource() = R.layout.fragment_repo_details

    override fun init(view: View, state: Bundle?) {

    }
}
