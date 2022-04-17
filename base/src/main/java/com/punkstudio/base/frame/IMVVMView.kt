package com.punkstudio.base.frame

import androidx.lifecycle.ViewModelStoreOwner


interface IMVVMView {

    /**
     * Create view data controller
     */
    fun createViewModel()

    /**
     * Initialize UI event component
     */
    fun registerUIEvent(vm: BaseViewModel)

    /**
     * Get view model owner
     */
    fun getOwner(): ViewModelStoreOwner

}