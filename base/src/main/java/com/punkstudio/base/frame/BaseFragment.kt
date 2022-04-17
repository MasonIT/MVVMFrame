package com.punkstudio.base.frame

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.punkstudio.base.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getResourceLayoutId(), container, false)
    }

    val baseActivity by lazy {
        requireActivity() as BaseActivity
    }

    abstract fun getResourceLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initViewListener(view)
        registerEventBus()
        //做初始化网络请求操作，该方法回调会在页面初始化成功之后触发，优化页面启动性能
        Looper.myQueue().addIdleHandler {
            initNetRequest()
            return@addIdleHandler false
        }
    }

    open fun initViewListener(view: View) {

    }

    open fun initView(view: View) {

    }

    open fun initNetRequest() {

    }

    protected open fun isRegisterEventBus() = false

    private fun registerEventBus() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    private fun unregisterEventBus() {
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

    protected open fun <T : Any> onEventBusMessageEvent(event: MessageEvent<T>) {}

    override fun onDestroy() {
        unregisterEventBus()
        super.onDestroy()
    }
}