package com.punkstudio.base.frame


class ResponseException : Exception {

    private var code: Int
    private var codeStr: String = ""
    var errorMsg: String

    constructor() {
        code = 0
        errorMsg = ""
    }

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errorMsg = error.getValue()
    }

    constructor(code: Int, msg: String? = "请求错误", e: Throwable? = null,codeStr:String="") : super(e) {
        this.code = code
        this.errorMsg = msg ?: "请求错误"
        this.codeStr = codeStr
    }

    constructor(base: BaseResult<*>, e: Throwable? = null) : super(e) {
        this.code = base.code ?: 0
        this.errorMsg = base.msg ?: "请求错误"
    }
}