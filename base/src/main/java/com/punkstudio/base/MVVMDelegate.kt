package com.punkstudio.base

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.punkstudio.base.di.component.BaseComponent
import com.punkstudio.base.di.component.DaggerBaseComponent
import com.punkstudio.base.di.module.BaseModule

object MVVMDelegate {

    lateinit var daggerBaseComponent: BaseComponent

    fun init(application: Application) {
        initBlankJ(application)
        initDagger(application)
    }

    private fun initBlankJ(application: Application) {
        Utils.init(application)
    }

    private fun initDagger(application: Application) {
        daggerBaseComponent = DaggerBaseComponent.builder()
            .baseModule(BaseModule(application))
            .build()
    }
}