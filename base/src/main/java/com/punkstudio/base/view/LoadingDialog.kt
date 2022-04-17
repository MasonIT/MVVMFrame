package com.punkstudio.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.punkstudio.base.R

class LoadingDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.base_loadding_dialog, container, false)
    }

    override fun getTheme(): Int {
        return R.style.BaseDialogBgDim
    }

    companion object {
        fun newInstance(
        ): LoadingDialog {
            return LoadingDialog()
        }
    }
}