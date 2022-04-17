package com.punkstudio.base.utils
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt


object StatusBarUtils {
    /**
     * 沉浸式处理
     */
    fun setImmersionBar(
        activity: Activity, @ColorInt color: Int,
        hideStatusBar: Boolean,
        isDark: Boolean
    ) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDark) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
        val winParams = window.attributes
        if (hideStatusBar) {
            winParams.flags =
                winParams.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        } else {
            winParams.flags =
                winParams.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        }
        window.attributes = winParams
    }
}