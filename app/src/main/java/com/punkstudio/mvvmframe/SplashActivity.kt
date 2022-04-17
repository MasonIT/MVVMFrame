package com.punkstudio.mvvmframe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.punkstudio.base.frame.BaseMVVMActivity
import com.punkstudio.base.frame.NoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseMVVMActivity<ViewDataBinding, NoViewModel>() {

    override fun getResourceLayoutId(): Int = R.layout.activity_splash

    override fun initViewModel() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                delay(3000L)
                navigate2Home()
            }
        }
    }

    private fun navigate2Home() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun isTransparent() = true
}