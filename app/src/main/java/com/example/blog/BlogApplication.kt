package com.example.blog

import android.app.Application
import com.example.blog.util.PreferencesUtil
import com.example.blog.util.TimberDebugTree
import com.google.firebase.FirebaseApp
import timber.log.Timber

class BlogApplication : Application(){

    companion object {
        lateinit var prefs: PreferencesUtil
        var USER_ID: Int = 0
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferencesUtil(applicationContext)
        USER_ID = prefs.getInt("userId", 0)
        FirebaseApp.initializeApp(applicationContext)
        if(BuildConfig.DEBUG){
            Timber.plant(TimberDebugTree())
        }
    }
}