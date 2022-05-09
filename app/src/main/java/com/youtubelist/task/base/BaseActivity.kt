package com.youtubelist.task.base

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.youtubelist.task.R
import com.youtubelist.task.utils.PrefUtils
import com.youtubelist.task.utils.showSnackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

open class BaseActivity<V : BaseViewModel>(private val viewModelClass: KClass<V>) : AppCompatActivity() {

    protected lateinit var viewModel: V
    private var lightStatusBar: Int? = null
    private val pref: PrefUtils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLightStatusColor()
        baseInit()
    }

    private fun getLightStatusColor() {
        lightStatusBar = window.decorView.systemUiVisibility
        pref.lightStatusColor = lightStatusBar!!
    }

    fun makeStatusBarLight() {
        window.statusBarColor =
            ContextCompat.getColor(this, R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else window.decorView.systemUiVisibility = pref.lightStatusColor
    }

    private fun baseInit() {
        viewModel = getViewModel(clazz = viewModelClass)
        viewModel.error.observe(this, errorObserver)
    }

    private val errorObserver = Observer<ErrorBody> {
        it?.let {
            showSnackbar(it.message ?: getString(R.string.error), true, activity = this)
        }
    }
}