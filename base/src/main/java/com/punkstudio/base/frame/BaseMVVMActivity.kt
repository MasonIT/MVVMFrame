package com.punkstudio.base.frame

import android.os.Bundle
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType

abstract class BaseMVVMActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity(),
    IMVVMView {

    protected lateinit var viewModel: VM

    protected val tag: String = javaClass.simpleName

    protected lateinit var dataBinding: DB

    //是否第一次加载
    private var isFirst: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel()
        intent?.let {
            viewModel.handleDataSource(it)
        }
        initViewModel()
        initView(savedInstanceState)
        registerEventBus()
        registerUIEvent(viewModel)
        configureListeners()
        Looper.myQueue().addIdleHandler {
            initNetRequest()
            return@addIdleHandler false
        }
    }

    open fun initNetRequest() {

    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    open fun lazyLoadData() {}

    open fun configureListeners() {}

    override fun setUpView() {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            dataBinding = DataBindingUtil.setContentView(this, getResourceLayoutId())
            dataBinding.lifecycleOwner = this
        } else {
            super.setUpView()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[1]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel =
                ViewModelProvider(getOwner().viewModelStore, ViewModelFactory())[tClass] as VM
        }
    }

    override fun registerUIEvent(vm: BaseViewModel) {
        vm.uiEvent.showLoading.observe(this, Observer {
            showLoading()
        })
        vm.uiEvent.hideLoading.observe(this, Observer {
            hideLoading()
        })
        vm.uiEvent.shortToastEvent.observe(this, Observer {
            ToastUtils.showShort(it)
        })
        vm.uiEvent.longToastEvent.observe(this, Observer {
            ToastUtils.showLong(it)
        })
        vm.uiEvent.progressEvent.observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun getOwner(): ViewModelStoreOwner = this

    abstract fun initViewModel()

    abstract fun initView(savedInstanceState: Bundle?)


    override fun onDestroy() {
        unregisterEventBus()
        super.onDestroy()
    }
}