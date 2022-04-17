package com.punkstudio.base.frame

import com.google.gson.annotations.SerializedName


data class BaseResult<T>(
    @SerializedName("code", alternate = ["Code"])
    val code: Int?,
    @SerializedName("msg", alternate = ["Msg", "message"])
    val msg: String?,
    @SerializedName("data", alternate = ["Data"])
    val data: T?,
    val isSuccess: Boolean = (code == 200)
)