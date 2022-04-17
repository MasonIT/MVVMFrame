package com.punkstudio.base.frame

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException


object ExceptionHandler {

    fun handleException(throwable: Throwable): ResponseException {
        val ex: ResponseException
        when (throwable) {
            is HttpException -> {
                ex = ResponseException(ERROR.HTTP_ERROR, throwable)
            }
            is JsonParseException, is JSONException, is ParseException,
            is MalformedJsonException -> {
                ex = ResponseException(ERROR.PARSE_ERROR, throwable)
            }
            is ConnectException -> {
                ex = ResponseException(ERROR.NETWORK_ERROR, throwable)
            }
            is SSLException -> {
                ex = ResponseException(ERROR.SSL_ERROR, throwable)
            }
            is SocketTimeoutException -> {
                ex = ResponseException(ERROR.TIMEOUT_ERROR, throwable)
            }
            is UnknownHostException -> {
                ex = ResponseException(ERROR.NETWORK_ERROR, throwable)
            }
            else -> {
                ex = if (!throwable.message.isNullOrEmpty()) ResponseException(
                    1000,
                    throwable.message.orEmpty(),
                    throwable
                )
                else ResponseException(ERROR.UNKNOWN, throwable)
            }
        }
        return ex
    }
}