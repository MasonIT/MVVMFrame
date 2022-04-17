package com.punkstudio.base.di.component

import android.app.Application
import com.punkstudio.base.di.module.BaseModule
import com.punkstudio.base.di.module.DataModule
import com.punkstudio.base.di.module.NetworkModule
import dagger.Component
import retrofit2.Retrofit


@Component(
    modules = [NetworkModule::class, BaseModule::class, DataModule::class]
)
interface BaseComponent {
    val application: Application
    val retrofit: Retrofit
}