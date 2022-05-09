package com.youtubelist.task.remote

import com.youtubelist.task.ui.main.model.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("videos")
    fun getPopularVideos(
        @Query("part") partSnippet: String = "snippet",
        @Query("part") partStats: String = "statistics",
        @Query("chart") chart: String = "mostPopular",
        @Query("key") key: String = GOOGLE_API_KEY,
        @Query("regionCode") country: String = listOf("us", "in", "ru").random(),
        @Query("maxResults") maxResults: Int = 20,
        @Query("pageToken") nextPageId: String? = null
    ): Call<YoutubeResponse>
}