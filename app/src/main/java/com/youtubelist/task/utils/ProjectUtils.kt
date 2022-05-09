package com.youtubelist.task.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.youtubelist.task.R
import com.youtubelist.task.application.YTApplication
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow


fun getStringResource(@StringRes res: Int) = YTApplication.getContext().getString(res)

fun showSnackbar(message: String, isError: Boolean = false, activity: Activity) {
    val context = YTApplication.getContext()
    val font = ResourcesCompat.getFont(context, R.font.varela_round_regular)
    val sb = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    sb.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).typeface = font
    if (isError)
        sb.setBackgroundTint(ContextCompat.getColor(context, R.color.error_red))
            .setTextColor(ContextCompat.getColor(context, R.color.white))
            .show()
    else
        sb.setBackgroundTint(ContextCompat.getColor(context, R.color.valid_blue))
            .setTextColor(ContextCompat.getColor(context, R.color.white)).setAction("Action", null)
            .show()
}

fun numberFormatter(number: Long): String? {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val value = floor(log10(number.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        DecimalFormat("#0.0").format(
            number / 10.0.pow((base * 3).toDouble())
        ) + suffix[base]
    } else {
        DecimalFormat("#,##0").format(number)
    }
}

 fun isAppInstalled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}