package com.punkstudio.base.data.local

interface ISharedPrefsManager {
    fun hasKey(key: String): Boolean
    fun addString(key: String, value: String)
    fun addStringSet(key: String, value: Set<String>)
    fun addBoolean(key: String, value: Boolean)
    fun getInt(key: String, defaultValue: Int): Int
    fun addLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long): Long
    fun addInt(key: String, value: Int)
    fun getString(key: String, defaultValue: String? = null): String?
    fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>?
    fun getBoolean(key: String, defaultValue: Boolean? = null): Boolean
    fun removeValue(key: String)
}