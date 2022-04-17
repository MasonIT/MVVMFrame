package com.punkstudio.base.utils

import androidx.fragment.app.FragmentManager
import com.punkstudio.base.view.LoadingDialog

/**
 * Show loading view
 */
fun showLoadingDialog(
    childFragmentManager: FragmentManager,
    tag: String,
    isCancelable: Boolean = true
) {
    hideLoadingDialog(childFragmentManager, tag)

    val mLoadingDialog = LoadingDialog.newInstance()
    mLoadingDialog.isCancelable = isCancelable

    val fragmentTransaction = childFragmentManager.beginTransaction()
    val prev = childFragmentManager.findFragmentByTag(tag)
    if (prev != null) {
        fragmentTransaction.remove(prev).commitAllowingStateLoss()
    } else {
        fragmentTransaction.addToBackStack(null)
        mLoadingDialog.show(fragmentTransaction, tag)
    }
}


/**
 * Hide loading view
 */
fun hideLoadingDialog(childFragmentManager: FragmentManager, tag: String) {
    val frag = childFragmentManager.findFragmentByTag(tag) ?: return
    val loading = frag as LoadingDialog
    loading.dismiss()
}