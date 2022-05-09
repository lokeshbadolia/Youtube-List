package com.youtubelist.task.ui.main.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.youtubelist.task.base.BaseViewModel
import com.youtubelist.task.ui.main.model.YoutubeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainViewModel : BaseViewModel() {
    fun getPaginatedPopularVideos(nextPageToken: String?) : MutableLiveData<YoutubeResponse?> {
        return  apiHelper.makeRequest(apiService.getPopularVideos(nextPageId = nextPageToken))
    }
}