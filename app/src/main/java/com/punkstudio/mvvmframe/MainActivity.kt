package com.punkstudio.mvvmframe

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.punkstudio.base.frame.BaseMVVMActivity
import com.punkstudio.base.frame.NoViewModel

class MainActivity : BaseMVVMActivity<ViewDataBinding, NoViewModel>() {

    override fun getResourceLayoutId(): Int = R.layout.activity_main

    override fun initViewModel() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun isTransparent(): Boolean {
        return false
    }

}