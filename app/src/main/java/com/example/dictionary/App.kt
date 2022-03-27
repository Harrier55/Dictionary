package com.example.dictionary

import android.app.Application
import com.example.dictionary.di.AppComponent
import com.example.dictionary.di.DaggerAppComponent
import com.example.dictionary.di.NetworkModule
import com.example.dictionary.di.StorageModule

class App : Application() {

    var appComponent =  DaggerAppComponent.builder()
        .storageModule(StorageModule())
        .networkModule(NetworkModule())
        .build()

    override fun onCreate() {
        super.onCreate()
        instance = this
//        appComponent = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}