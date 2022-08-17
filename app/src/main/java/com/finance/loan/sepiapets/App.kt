package com.finance.loan.sepiapets

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        @Volatile
        private lateinit var instance: App
//        fun getInstance() = instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}