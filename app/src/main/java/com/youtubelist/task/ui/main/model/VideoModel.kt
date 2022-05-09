package com.youtubelist.task.ui.main.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class YoutubeResponse(
    @field:SerializedName("kind")
    val kind: String? = null,

    @field:SerializedName("etag")
    val etag: String? = null,

    @field:SerializedName("items")
    val items: ArrayList<YoutubeItem>? = null,

    @field:SerializedName("nextPageToken")
    val nextPageToken: String = "",

    @field:SerializedName("prevPageToken")
    val prevPageToken: String? = null,

    @field:SerializedName("pageInfo")
    val pageInfo: PageInfo? = null,
) : Parcelable

@Parcelize
data class Error(
    @field:SerializedName("code")
    val code: Int = 0,

    @field:SerializedName("status")
    val status: String = "",

    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("errors")
    val items: List<ErrorDetail>? = null
) : Parcelable


@Parcelize
data class ErrorDetail(
    @field:SerializedName("message")
    val message: String = "",

    @field:SerializedName("domain")
    val domain: String = "",

    @field:SerializedName("reason")
    val reason: String = ""
) : Parcelable


@Parcelize
data class YoutubeItem(
    @field:SerializedName("kind")
    val kind: String? = null,

    @field:SerializedName("etag")
    val etag: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("snippet")
    val snippet: Snippet? = null,

    @field:SerializedName("statistics")
    val statistics: Statistics? = null
) : Parcelable


@Parcelize
data class PageInfo(
    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("resultsPerPage")
    val resultsPerPage: Int? = null
) : Parcelable


@Parcelize
data class Snippet(
    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("channelId")
    val channelId: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("thumbnails")
    val thumbnails: Thumbnails? = null,

    @field:SerializedName("channelTitle")
    val channelTitle: String? = null,
/*
	@field:SerializedName("tags")
	val tags: List<String?>? = null,*/

    @field:SerializedName("categoryId")
    val categoryId: String? = null,

    @field:SerializedName("liveBroadcastContent")
    val liveBroadcastContent: String? = null,

    @field:SerializedName("localized")
    val localized: Localized? = null,

    @field:SerializedName("defaultAudioLanguage")
    val defaultAudioLanguage: String? = null,

    @field:SerializedName("defaultLanguage")
    val defaultLanguage: String? = null
) : Parcelable


@Parcelize
data class Statistics(
    @field:SerializedName("dislikeCount")
    val dislikeCount: String? = null,

    @field:SerializedName("likeCount")
    val likeCount: String? = null,

    @field:SerializedName("viewCount")
    val viewCount: String? = null,

    @field:SerializedName("favoriteCount")
    val favoriteCount: String? = null,

    @field:SerializedName("commentCount")
    val commentCount: String? = null
) : Parcelable


@Parcelize
data class Thumbnails(
    @field:SerializedName("default")
    val default: ThumbnailData? = null,

    @field:SerializedName("high")
    val high: ThumbnailData? = null,

    @field:SerializedName("medium")
    val medium: ThumbnailData? = null,

    @field:SerializedName("channelTitle")
    val channelTitle: String = ""
) : Parcelable


@Parcelize
data class ThumbnailData(
    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("height")
    val height: Int? = null
) : Parcelable


@Parcelize
data class Localized(
    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null
) : Parcelable