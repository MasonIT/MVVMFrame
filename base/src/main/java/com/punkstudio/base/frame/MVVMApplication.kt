package com.punkstudio.base.frame

import android.app.Application
import com.punkstudio.base.MVVMDelegate


open class MVVMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MVVMDelegate.init(this)
    }
}