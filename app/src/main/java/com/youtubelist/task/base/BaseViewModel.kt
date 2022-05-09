package com.youtubelist.task.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youtubelist.task.remote.ApiHelper
import com.youtubelist.task.remote.ApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(), KoinComponent {

    val apiHelper: ApiHelper by inject()
    val apiService: ApiService by inject()
    var error = MutableLiveData<ErrorBody>(null)
    var apiResponse = MutableLiveData<Any?>()

    init {
        apiHelper.setHandlers(error)
    }

}