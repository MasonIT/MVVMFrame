package com.punkstudio.base.frame

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.punkstudio.base.R
import com.punkstudio.base.event.MessageEvent
import com.punkstudio.base.utils.StatusBarUtils
import com.punkstudio.base.utils.hideLoadingDialog
import com.punkstudio.base.utils.showLoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

const val DIALOG_LOADING_TAG = "loading"

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = initRequestOrientation()
        super.onCreate(savedInstanceState)
        if (isTransparent()) {
            setTransparentStatusBar()
        } else {
            setStatusBar()
        }
        setUpView()
    }

    open fun isAddAnim(): Boolean = true

    /**
     * Hide loading view
     */
    fun hideLoading() {
        hideLoadingDialog(supportFragmentManager, DIALOG_LOADING_TAG)
    }

    /**
     * Show loading view
     */
    fun showLoading(isCancelable: Boolean = true) {
        showLoadingDialog(supportFragmentManager, DIALOG_LOADING_TAG, isCancelable)
    }

    open fun setUpView() {
        if (getResourceLayoutId() != 0) {
            setContentView(getResourceLayoutId())
        }
    }

    /**
     * Set status bar transparent
     */
    private fun setTransparentStatusBar() {
        StatusBarUtils.setImmersionBar(
            this, Color.TRANSPARENT,
            hideStatusBar = false,
            isDark = true
        )
    }

    /**
     * Set status [color], set [hideStatusBar] hide status bar and set if [isDark] status bar is dark
     */
    protected open fun setStatusBar(
        @ColorInt color: Int = getColor(R.color.colorPrimaryDark), hideStatusBar: Boolean = false,
        isDark: Boolean = true
    ) {
        StatusBarUtils.setImmersionBar(
            this, color, hideStatusBar, isDark
        )
    }


    protected fun registerEventBus() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    protected fun unregisterEventBus() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T : Any> onMessageEvent(event: MessageEvent<T>?) {
        if (event != null) {
            this.onEventBusMessageEvent(event)
        }
    }

    protected open fun initRequestOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * Receive global event. [event]: data source
     */
    protected open fun <T : Any> onEventBusMessageEvent(event: MessageEvent<T>) {}

    /**
     * Whether register event bus. Default don't
     */
    protected open fun isRegisterEventBus() = false

    /**
     * Whether status bar is transparent. Default it is
     */
    protected open fun isTransparent() = true

    /**
     * Get content id from View
     */
    protected open fun getContentId() = 0

    /**
     * Get view root id
     */
    abstract fun getResourceLayoutId(): Int


    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        if (isAddAnim()) {
            overridePendingTransition(R.anim.base_nav_enter, R.anim.base_nav_exit)
        }
    }

    override fun finish() {
        super.finish()
        if (isAddAnim()) {
            overridePendingTransition(R.anim.base_nav_pop_enter, R.anim.base_nav_pop_exit)
        }
    }
}