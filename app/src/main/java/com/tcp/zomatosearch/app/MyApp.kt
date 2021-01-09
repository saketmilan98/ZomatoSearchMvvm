package com.tcp.zomatosearch.app

import android.app.Application


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }

    companion object{
        private var currentApplication: MyApp? = null
        fun getInstance(): MyApp? {
            return currentApplication
        }
        val GPS_REQUEST = 1002
        val BASE_URL = "https://developers.zomato.com/api/v2.1/"
    }
}