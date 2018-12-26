package com.tonia.githubandroidtrending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Base fragment, that all concrete fragments should extend.
 */
abstract class BaseFragment : Fragment(), BaseView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getContentResource(), container, false)
        init(view, savedInstanceState)

        return view
    }
}
