package com.youtubelist.task.koin

import android.content.Context
import com.google.gson.GsonBuilder
import com.youtubelist.task.BuildConfig
import com.youtubelist.task.R
import com.youtubelist.task.remote.ApiHelper
import com.youtubelist.task.remote.ApiService
import com.youtubelist.task.remote.BASE_URL
import com.youtubelist.task.utils.PrefUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val koinModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(if (BuildConfig.BUILD_TYPE == "debug") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                var request = chain.request()
                request =
                    request.newBuilder().addHeader("version", BuildConfig.VERSION_NAME).build()
                request = request.newBuilder().addHeader("Accept", "application/json").build()
                request = request.newBuilder().addHeader("device", "android").build()
                chain.proceed(request)
            }).build()
    }

    single {
        val client = Retrofit.Builder().baseUrl(BASE_URL).client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        client.create(ApiService::class.java)
    }

    factory { ApiHelper() }
}

val prefModules = module {
    single {
        androidContext().getSharedPreferences(
            "${androidContext().getString(R.string.app_name)}_preferences", Context.MODE_PRIVATE
        )
    }
    single { PrefUtils(get()) }
}