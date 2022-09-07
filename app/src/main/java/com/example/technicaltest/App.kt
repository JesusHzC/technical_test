package com.example.technicaltest

import android.app.Application
import com.example.technicaltest.services.FirebaseCloud
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var firebaseCloud: FirebaseCloud
    }

    override fun onCreate() {
        super.onCreate()
        firebaseCloud = FirebaseCloud()
        firebaseCloud.initInstance()
    }

}