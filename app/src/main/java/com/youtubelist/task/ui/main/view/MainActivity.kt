package com.youtubelist.task.ui.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.youtubelist.task.ui.main.adapter.VideoListAdapter
import com.youtubelist.task.base.BaseActivity
import com.youtubelist.task.databinding.ActivityMainBinding
import com.youtubelist.task.listener.ItemClickListener
import com.youtubelist.task.ui.main.model.YoutubeItem
import com.youtubelist.task.ui.main.model.YoutubeResponse
import com.youtubelist.task.ui.main.viewmodel.MainViewModel
import com.youtubelist.task.utils.isAppInstalled


class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class),
    ViewTreeObserver.OnScrollChangedListener {

    private lateinit var binding: ActivityMainBinding
    private var nextPageToken: String? = null
    private var listAdapter: VideoListAdapter? = null

    private var next = false
    private var scrollPositionChanged = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        makeStatusBarLight()
        init()
        setupRecycler()
        callApi()
    }

    private fun init() {
        binding.refreshBtn.setOnClickListener { refresh() }
    }

    private fun setupRecycler() {
        listAdapter = VideoListAdapter(this, videoClickListener)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            isNestedScrollingEnabled = false
            adapter = listAdapter
        }
        binding.dataView.viewTreeObserver.addOnScrollChangedListener(this)
    }

    private val videoClickListener = ItemClickListener<YoutubeItem> {
        openYoutube(it)
    }

    private fun openYoutube(video: YoutubeItem) {
        if (isAppInstalled("com.google.android.youtube", packageManager)) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.id}")))
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=${video.id}")
                )
            )
        }
    }

    private fun callApi() {
        viewModel.getPaginatedPopularVideos(nextPageToken).observe(this){
            if (it != null) {
                binding.progressBar.visibility = GONE
                showData()
                listAdapter!!.addVideos(it.items)
                if (it.nextPageToken != "") {
                    nextPageToken = it.nextPageToken
                    next = true
                } else {
                    nextPageToken = null
                    next = false
                }
            } else showEmpty()
        }
    }

    override fun onScrollChanged() {
        if (next) {
            val view: View =
                binding.dataView.getChildAt(binding.dataView.childCount - 1)
            val diff: Int =
                view.bottom - (binding.dataView.height + binding.dataView.scrollY)
            if (diff <= 500) {
                if (scrollPositionChanged) {
                    scrollPositionChanged = false
                    binding.progressBar.visibility = VISIBLE
                    callApi()
                    next = false
                }
            } else scrollPositionChanged = true
        }
    }

    private fun refresh() {
        listAdapter?.refresh()
        nextPageToken = null
        showShimmer()
        callApi()
    }

    private fun showData() {
        binding.dataView.visibility = VISIBLE
        binding.noDataView.visibility = GONE
        binding.shimmerView.visibility = GONE
    }

    private fun showEmpty() {
        binding.dataView.visibility = GONE
        binding.noDataView.visibility = VISIBLE
        binding.shimmerView.visibility = GONE
    }

    private fun showShimmer() {
        binding.dataView.visibility = GONE
        binding.noDataView.visibility = GONE
        binding.shimmerView.visibility = VISIBLE
    }
}