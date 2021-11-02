package com.example.blog

import android.app.Application
import com.example.blog.util.PreferencesUtil
import com.example.blog.util.TimberDebugTree
import com.google.firebase.FirebaseApp
import timber.log.Timber

class BlogApplication : Application(){

    companion object {
        lateinit var prefs: PreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferencesUtil(applicationContext)
        FirebaseApp.initializeApp(applicationContext)
        if(BuildConfig.DEBUG){
            Timber.plant(TimberDebugTree())
        }
    }
}