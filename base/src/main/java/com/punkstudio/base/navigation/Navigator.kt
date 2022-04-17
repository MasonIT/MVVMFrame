package com.punkstudio.base.navigation

import android.os.Bundle


interface Navigator {
    fun navigateTo(navId: Int, navHostName: Int, navArgs: Bundle? = null)
    fun stepBack(navHostName: Int)
    fun stepBackTo(navId: Int, navHostName: Int, inclusive: Boolean)
}