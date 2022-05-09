package com.youtubelist.task.remote

import android.accounts.NetworkErrorException
import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.youtubelist.task.R
import com.youtubelist.task.base.ErrorBody
import com.youtubelist.task.utils.getStringResource
import com.youtubelist.task.utils.showSnackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.TimeoutException

class ApiHelper {

    private var error = MutableLiveData<ErrorBody>(null)

    fun setHandlers(error: MutableLiveData<ErrorBody>) {
        this.error = error
    }

    fun <T> makeRequest(api: Call<T>): MutableLiveData<T?> {
        val responseHolder = MutableLiveData<T?>()
        api.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == 200) {
                    responseHolder.value = response.body()
                } else {
                    responseHolder.value = null
                    handleError(response.code(), response.errorBody())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                responseHolder.value = null
                getErrorOrShowMessage(t)
            }
        })
        return responseHolder
    }

    private fun getErrorOrShowMessage(
        error: Throwable,
        show: Boolean = true
    ): String {
        Log.e(getStringResource(R.string.app_name), error.localizedMessage, error)
        val message = when (error) {
            is NetworkErrorException -> getStringResource(R.string.no_int_connection)
            is ParseException -> getStringResource(R.string.parsing_error)
            is TimeoutException -> getStringResource(R.string.slow_internet)
            is SocketTimeoutException -> getStringResource(R.string.could_not_reach_server)
            is UnknownHostException -> getStringResource(R.string.could_not_reach_server)
            is ConnectException -> getStringResource(R.string.no_int_connection)
            else -> getStringResource(R.string.api_error)
        }
        if (show && message.isNotEmpty()) {
            this.error.value = ErrorBody(status = false, statusCode = 500, message = message)
        }
        return message
    }

    private fun handleError(code: Int, errorBody: ResponseBody?) {
        var errorMessage = ""
        when (code) {
            500 -> errorMessage = getStringResource(R.string.internal_server_error)
            403 -> errorMessage = getStringResource(R.string.api_key_error)
            else -> {
                try {
                    Gson().fromJson(errorBody?.string(), ErrorBody::class.java)?.let {
                        errorMessage = it.message ?: getStringResource(R.string.api_error)
                    }
                } catch (e: Exception) {
                    errorMessage = getErrorOrShowMessage(e, false)
                }
            }
        }
        error.value = ErrorBody(status = false, statusCode = code, message = errorMessage)
    }
}