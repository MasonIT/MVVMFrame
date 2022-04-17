
package com.punkstudio.base.event

class MessageEvent<T: Any> {

    var code: Int
        private set
    var data: T? = null
        private set

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }

    // CODE_{MODULE_NAME}_{BUSINESS}
    companion object {
        const val CODE_LOGIN_BIND_WECHAT = 0x1001
        const val CODE_LOGIN_SUCCESS = 0x1002
        const val CODE_LOGOUT_SUCCESS = 0x1003
        const val CODE_BOC_BACK = 0x1004
        const val CODE_DONGZHENG_BACK = 0x1005
        const val CODE_ORDER_REFUND = 0x1006
        const val CODE_ORDER_CHECKOUT = 0x1007
        const val CODE_CONFIGURATOR_CHECKOUT = 0x1008
        const val CODE_WEBVIEW_ACTIVITY_FINISH = 0x1009
        const val CODE_CASHIER_FINISHED = 0x1010
        const val CODE_CASHIER_PAY_OFF = 0x1011//付清（把传过来的金额都支付了）
        const val CODE_PAB_BACK = 0x1012
        const val CODE_CAR_OWNER_INFO_UPDATED = 0x1013
        const val CODE_WALL_BOX_EDIT_NICKNAME_SUCCESS = 0x1014
    }
}

data class SubmitEvent(
    var isShow: Boolean = false,
    var remind: String = ""
)