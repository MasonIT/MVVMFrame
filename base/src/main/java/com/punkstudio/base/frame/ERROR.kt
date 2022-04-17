package com.punkstudio.base.frame


enum class ERROR(private val code: Int, private val err: String) {
    /**
     * 未知错误
     */
    UNKNOWN(1000, "未知错误"),
    /**
     * Gson解析错误
     */
    PARSE_ERROR(1001, "解析错误"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络错误"),
    /**
     * 请求出错
     */
    HTTP_ERROR(1003, "请求出错"),
    /**
     * 证书出错
     */
    SSL_ERROR(1004, "网络出错"),
    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1005, "连接超时");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }
}