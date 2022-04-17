package com.punkstudio.base.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
open class BaseModule(private var mApplication: Application) {

    @Provides
    open fun provideApplication(): Application = mApplication

    @Provides
    open fun provideContext(): Context = mApplication.applicationContext

}