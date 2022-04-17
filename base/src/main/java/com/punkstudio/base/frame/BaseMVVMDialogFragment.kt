package com.punkstudio.base.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.reflect.ParameterizedType

abstract class BaseMVVMDialogFragment<DB : ViewDataBinding, VM : BaseViewModel> : DialogFragment(),
    IMVVMView {

    protected lateinit var viewModel: VM

    protected val logTag: String = javaClass.simpleName

    protected var dataBinding: DB? = null

    //是否第一次加载
    private var isFirst: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cls =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            dataBinding = DataBindingUtil.inflate(inflater, getResourceLayoutId(), container, false)
            dataBinding?.lifecycleOwner = this
            return dataBinding?.root
        }
        return inflater.inflate(getResourceLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        createViewModel()
        lifecycle.addObserver(viewModel)
        configureListeners()
        initView(savedInstanceState)
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

    override fun registerUIEvent(vm: BaseViewModel) {}

    @Suppress("UNCHECKED_CAST")
    override fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[1]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(getOwner().viewModelStore, ViewModelFactory())[tClass] as VM
        }
    }

    abstract fun getResourceLayoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    override fun getOwner(): ViewModelStoreOwner = this
}