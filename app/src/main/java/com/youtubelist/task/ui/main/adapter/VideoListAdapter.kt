package com.youtubelist.task.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.youtubelist.task.R
import com.youtubelist.task.databinding.RowVideoBinding
import com.youtubelist.task.listener.ItemClickListener
import com.youtubelist.task.ui.main.model.YoutubeItem
import com.youtubelist.task.utils.numberFormatter

class VideoListAdapter(
    private val context: Context,
    private val listener: ItemClickListener<YoutubeItem>
) :
    RecyclerView.Adapter<VideoListAdapter.MyViewHolder>() {

    private val videoList: ArrayList<YoutubeItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(RowVideoBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (videoList.isNotEmpty() && videoList.size > 0) {
            val video = videoList[position]
            fillData(holder, video)
        }
    }

    private fun fillData(holder: MyViewHolder, video: YoutubeItem) {
        if (video.snippet != null) {
            val snippet = video.snippet
            holder.binding.videoTitle.text = snippet.title

            if (snippet.thumbnails != null) {
                val thumbnail = video.snippet.thumbnails
                Glide.with(context).load(thumbnail?.high?.url).apply(
                    RequestOptions().placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                ).into(holder.binding.thumbnailImage)

                Glide.with(context).load(thumbnail?.default?.url).apply(
                    RequestOptions().placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                ).into(holder.binding.channelIcon)
            }
            holder.binding.channelName.text = snippet.channelTitle
        }

        if (video.statistics != null) {
            val statistics = video.statistics
            val likeCount = statistics.likeCount

            if (likeCount != null && likeCount != "" && likeCount != "0")
                holder.binding.videoLikes.text = numberFormatter(statistics.likeCount.toLong())
            else holder.binding.videoLikes.text = "0"

            val commentCount = statistics.commentCount
            if (commentCount != null && commentCount != "" && commentCount != "0")
                holder.binding.videoComments.text =
                    numberFormatter(statistics.commentCount.toLong())
            else holder.binding.videoComments.text = "0"

            val viewCount = statistics.viewCount
            if (viewCount != null && viewCount != "" && viewCount != "0")
                holder.binding.videoSeen.text =
                    numberFormatter(statistics.viewCount.toLong())
            else holder.binding.videoSeen.text = "0"
        }

        holder.binding.clickView.setOnClickListener { listener.onClick(video) }
    }

    fun addVideos(list: ArrayList<YoutubeItem>?) {
        if (list != null) {
            videoList.addAll(list)
            notifyItemInserted(videoList.size)
        }
    }

    fun refresh() {
        videoList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (videoList.isNotEmpty()) videoList.size else 0
    class MyViewHolder(val binding: RowVideoBinding) : RecyclerView.ViewHolder(binding.root)
}