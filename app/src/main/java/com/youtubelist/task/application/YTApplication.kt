package com.youtubelist.task.application

import android.app.Application
import android.content.Context
import com.youtubelist.task.koin.koinModule
import com.youtubelist.task.koin.prefModules
import com.youtubelist.task.koin.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YTApplication : Application() {

    companion object {
        private lateinit var appContext: Context
        fun getContext() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        initKoin()
    }


    private fun initKoin() {
        startKoin {
            androidContext(this@YTApplication)
            modules(koinModule, prefModules, viewModelsModule)
        }
    }
}