package com.youtubelist.task.base

data class ErrorBody(
        var status: Boolean? = null,
        var statusCode: Int? = null,
        var message: String? = null,
)