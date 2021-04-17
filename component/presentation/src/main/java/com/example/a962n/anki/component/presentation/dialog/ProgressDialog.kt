package com.example.a962n.anki.component.presentation.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.a962n.anki.component.presentation.R


class ProgressDialog : DialogFragment() {

    companion object {
        private val TAG = ProgressDialog::class.java.simpleName

        fun show(manager: FragmentManager?) {
            manager?.apply {
                ProgressDialog().show(this, TAG)
            }
        }
        fun dismiss(manager: FragmentManager?) {
            manager?.apply {
                val f = this.findFragmentByTag(TAG)
                if (f is DialogFragment) {
                    f.dismiss()
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw Exception("activity null!!")
        val dialog =  Dialog(activity)
        dialog.window?.apply {
            this.requestFeature(Window.FEATURE_NO_TITLE)
            this.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            this.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setContentView(R.layout.dialog_progress)
        isCancelable = false
        return dialog
    }
}