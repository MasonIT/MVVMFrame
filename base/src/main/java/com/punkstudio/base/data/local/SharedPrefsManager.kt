package com.punkstudio.base.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager private constructor(context: Context) : ISharedPrefsManager {

    companion object {
        const val SHARED_PREFS_KEY = "shared_prefs"

        private var instance: SharedPrefsManager? = null

        fun instance(context: Context): SharedPrefsManager {
            if (instance == null) {
                instance = SharedPrefsManager(context)
            }
            return instance!!
        }
    }

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)

    override fun hasKey(key: String): Boolean {
        return sharedPrefs.contains(key)
    }

    override fun addString(key: String, value: String) {
        sharedPrefs.edit()
            .putString(key, value)
            .apply()
    }

    override fun addStringSet(key: String, value: Set<String>) {
        sharedPrefs.edit()
            .putStringSet(key, value)
            .apply()
    }

    override fun addBoolean(key: String, value: Boolean) {
        sharedPrefs.edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPrefs.getString(key, defaultValue)
    }

    override fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? {
        return sharedPrefs.getStringSet(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean?): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue ?: false)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPrefs.getLong(key, defaultValue)
    }

    override fun addLong(key: String, value: Long) {
        sharedPrefs.edit()
            .putLong(key, value)
            .apply()
    }

    override fun addInt(key: String, value: Int) {
        sharedPrefs.edit()
            .putInt(key, value)
            .apply()
    }

    override fun removeValue(key: String) {
        sharedPrefs.edit()
            .remove(key)
            .apply()
    }
}
