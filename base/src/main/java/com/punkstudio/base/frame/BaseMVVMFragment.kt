package com.punkstudio.base.frame

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.punkstudio.base.event.MessageEvent
import com.punkstudio.base.navigation.Navigator
import com.punkstudio.base.view.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

abstract class BaseMVVMFragment<DB : ViewDataBinding, VM : BaseViewModel> :
    Fragment(),
    IMVVMView,
    Navigator {

    protected lateinit var viewModel: VM

    private var viewRoot: View? = null

    protected val logTag: String = javaClass.simpleName

    protected lateinit var dataBinding: DB

    private val mLoadingDialog: LoadingDialog by lazy { LoadingDialog() } //加载菊花转

    //是否第一次加载
    private var isFirst: Boolean = true

    var type: Any? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        if (viewRoot != null && isEnableViewRootCache()) {
            view = viewRoot!!
        } else {
            type = javaClass.genericSuperclass
            while (type !is ParameterizedType) {
                if (type is Class<*>) {
                    type = (type as Class<*>).genericSuperclass
                }
            }
            val cls =
                (type as ParameterizedType).actualTypeArguments[0] as Class<*>
            if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(
                    cls
                )
            ) {
                dataBinding =
                    DataBindingUtil.inflate(inflater, getResourceLayoutId(), container, false)!!
                dataBinding.lifecycleOwner = this

                view = dataBinding.root
            } else {
                view = inflater.inflate(getResourceLayoutId(), container, false)
            }

            viewRoot = view
        }
        return view
    }

    open fun isEnableViewRootCache() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        createViewModel()
        lifecycle.addObserver(viewModel)
        registerUIEvent(viewModel)
        configureListeners()
        registerEventBus()
        initView(savedInstanceState)
        //做初始化网络请求操作，该方法回调会在页面初始化成功之后触发，优化页面启动性能
        Looper.myQueue().addIdleHandler {
            initNetRequest()
            return@addIdleHandler false
        }
    }

    open fun backClick(): Boolean {
        return try {
            findNavController().navigateUp()
        } catch (e: Exception) {
            false
        }
    }


    open fun initNetRequest() {

    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }
    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    open fun configureListeners() {}

    @Suppress("UNCHECKED_CAST")
    override fun createViewModel() {
        val tp = (type as ParameterizedType).actualTypeArguments[1]
        val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
        viewModel =
            ViewModelProvider(getOwner().viewModelStore, ViewModelFactory())[tClass] as VM
    }

    override fun registerUIEvent(vm: BaseViewModel) {
        vm.uiEvent.showLoading.observe(getOwner() as LifecycleOwner, Observer {
            showLoading()
        })
        vm.uiEvent.hideLoading.observe(getOwner() as LifecycleOwner, Observer {
            hideLoading()
        })
        vm.uiEvent.shortToastEvent.observe(getOwner() as LifecycleOwner, Observer {
            ToastUtils.showShort(it)
        })
        vm.uiEvent.longToastEvent.observe(getOwner() as LifecycleOwner, Observer {
            ToastUtils.showLong(it)
        })
        vm.uiEvent.progressEvent.observe(getOwner() as LifecycleOwner, Observer {
            if (it) showLoading() else hideLoading()
        })
    }

    /**
     * Hide loading view
     */
    private fun hideLoading() {
        lifecycleScope.launch {
            delay(100)
            if (mLoadingDialog.isVisible) {
                mLoadingDialog.dismissAllowingStateLoss()
            }
        }
    }

    /**
     * Show loading view
     */
    private fun showLoading(isCancelable: Boolean = true) {
        if (mLoadingDialog.isAdded) {
            return
        } else {
            if (!mLoadingDialog.isVisible) {
                mLoadingDialog.isCancelable = isCancelable
                if (!childFragmentManager.isStateSaved) {
                    mLoadingDialog.showNow(childFragmentManager, "loading")
                }
            }
        }
    }

    abstract fun getResourceLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    override fun getOwner(): ViewModelStoreOwner = this

    protected open fun isRegisterEventBus() = false

    private fun registerEventBus() {
        if (isRegisterEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun unregisterEventBus() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun <T : Any> onMessageEvent(event: MessageEvent<T>?) {
        if (event != null) {
            this.onEventBusMessageEvent(event)
        }
    }

    protected open fun <T : Any> onEventBusMessageEvent(event: MessageEvent<T>) {}

    override fun onDestroy() {
        unregisterEventBus()
        super.onDestroy()
    }

    override fun navigateTo(navId: Int, navHostName: Int, navArgs: Bundle?) {

        with(Navigation.findNavController(requireActivity(), navHostName)) {
            currentDestination
                ?.getAction(navId)
                ?.let {
                    navigate(navId, navArgs)
                }
        }
    }

    override fun stepBack(navHostName: Int) {
        Navigation.findNavController(requireActivity(), navHostName).popBackStack()
    }

    override fun stepBackTo(navId: Int, navHostName: Int, inclusive: Boolean) {
        Navigation.findNavController(requireActivity(), navHostName).popBackStack(navId, inclusive)
    }
}