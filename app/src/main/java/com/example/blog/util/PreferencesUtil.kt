package com.example.blog.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String{
        return prefs.getString(key, defValue).toString()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean{
        return prefs.getBoolean(key, defValue)
    }

    fun setString(key: String, str: String){
        prefs.edit().putString(key, str).apply()
    }

    fun setBoolean(key: String, bool: Boolean){
        prefs.edit().putBoolean(key, bool).apply()
    }

    fun removeValue(key: String){
        prefs.edit().remove(key).apply()
    }
}