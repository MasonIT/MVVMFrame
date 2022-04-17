package com.punkstudio.base.frame

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


open class BaseViewModel : AndroidViewModel(Utils.getApp()), LifecycleObserver {

    val uiEvent: UIEvent by lazy { UIEvent() }

    protected val app by lazy {
        getApplication<Application>()
    }

    /**
     * handle data source
     */
    open fun handleDataSource(intent: Intent) {

    }

    open fun initNetRequest() {
    }

    /**
     * Launch [block] in coroutine
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    /**
     * Transform [block] to flow responsive programming
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> = flow { emit(block()) }

    fun request(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseException) -> Unit = {
            uiEvent.shortToastEvent.value = it.errorMsg
        },
        complete: suspend CoroutineScope.() -> Unit = {},
        showLoading: Boolean = true,
        loadingText: String = "加载中"
    ) {
        if (showLoading) uiEvent.showLoading.call()
        launch {
            handleResponse(
                withContext(Dispatchers.IO) { block },
                { error(it) },
                {
                    uiEvent.hideLoading.call()
                    complete()
                }
            )
        }
    }


    private suspend fun handleResponse(
        block: suspend CoroutineScope.() -> Unit,
        error: suspend CoroutineScope.(ResponseException) -> Unit,
        complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        coroutineScope {
            try {
                block()
            } catch (t: Throwable) {
                error(ExceptionHandler.handleException(t))
                t.printStackTrace()
            } finally {
                complete()
            }
        }
    }

    fun <T> requestForResult(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success: (T) -> Unit,
        error: suspend CoroutineScope.(ResponseException) -> Unit = {
            uiEvent.shortToastEvent.value = it.errorMsg
        },
        complete: suspend CoroutineScope.() -> Unit = {},
        showProgress: Boolean = true
    ) {
        if (showProgress) uiEvent.progressEvent.value = true
        launch {
            handleResponses(
                withContext(Dispatchers.IO) { block },
                { res ->
                    filterResponses(res) {
                        if (it != null) {
                            success(it)
                        }
                    }
                },
                { error(it) },
                {
                    uiEvent.progressEvent.value = false
                    complete()
                }
            )
        }
    }

    private suspend fun <T> handleResponses(
        block: suspend CoroutineScope.() -> BaseResult<T>,
        success: suspend CoroutineScope.(BaseResult<T>) -> Unit,
        error: suspend CoroutineScope.(ResponseException) -> Unit,
        complete: suspend CoroutineScope.() -> Unit = {}
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (t: Throwable) {
                error(ExceptionHandler.handleException(t))
            } finally {
                complete()
            }
        }
    }

    private suspend fun <T> filterResponses(
        response: BaseResult<T>,
        success: suspend CoroutineScope.(T?) -> Unit
    ) {
        coroutineScope {
            if (response.data != null) success(response.data)
            else throw ResponseException(response)
        }
    }

    /**
     * UI event manager
     */
    inner class UIEvent {
        val hideLoading by lazy { SingleLiveEvent<String>() }
        val showLoading by lazy { SingleLiveEvent<String>() }
        val progressEvent by lazy { SingleLiveEvent<Boolean>() }
        val shortToastEvent by lazy { SingleLiveEvent<String>() }
        val longToastEvent by lazy { SingleLiveEvent<String>() }
    }

}