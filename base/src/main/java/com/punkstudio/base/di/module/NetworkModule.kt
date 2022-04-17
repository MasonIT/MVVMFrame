package com.punkstudio.base.di.module

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.punkstudio.base.data.remote.Api
import com.punkstudio.base.data.remote.RetrofitFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
open class NetworkModule {

    @Provides
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideGson(): Gson = with(GsonBuilder()) {
        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        create()
    }

    @Provides
    fun provideOkhttpClient(cache: Cache, builder: OkHttpClient.Builder): OkHttpClient = builder.build()

    @Provides
    fun provideOkhttpBuilder(
        interceptor: HttpLoggingInterceptor,
        application: Application
    ): OkHttpClient.Builder = with(OkHttpClient.Builder()) {
        connectTimeout(10, TimeUnit.SECONDS)
        readTimeout(10, TimeUnit.SECONDS)
        addInterceptor(interceptor)
    }

    @Provides
    open fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.instance.retrofit
    }


    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}