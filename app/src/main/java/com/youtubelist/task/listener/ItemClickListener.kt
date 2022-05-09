package com.youtubelist.task.listener


fun interface ItemClickListener<T> {
    fun onClick(result: T)
}