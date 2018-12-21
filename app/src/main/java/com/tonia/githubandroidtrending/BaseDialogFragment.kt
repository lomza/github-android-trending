package com.tonia.githubandroidtrending

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class BaseDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var title = ""
        var message = ""

        arguments?.let {
            title = getString(it.getInt(EXTRA_TITLE))
            message = getString(it.getInt(EXTRA_MESSAGE))
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()
    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_MESSAGE = "message"

        fun newInstance(@StringRes title: Int, @StringRes message: Int): BaseDialogFragment {
            val fragment = BaseDialogFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_TITLE, title)
            bundle.putInt(EXTRA_MESSAGE, message)
            fragment.arguments = bundle

            return fragment
        }
    }
}
