package com.youtubelist.task.koin

import com.youtubelist.task.ui.main.viewmodel.MainViewModel
import com.youtubelist.task.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { BaseViewModel() }
    viewModel { MainViewModel() }
}