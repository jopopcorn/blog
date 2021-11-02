package com.example.blog

import android.app.Application
import com.example.blog.util.PreferencesUtil
import com.google.firebase.FirebaseApp

class BlogApplication : Application(){

    companion object {
        lateinit var prefs: PreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferencesUtil(applicationContext)
        FirebaseApp.initializeApp(applicationContext)
    }
}