package com.example.dictionary

import android.app.Application
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.di.*
import com.example.dictionary.ui.MainActivity
import com.example.dictionary.ui.MainActivityPresenter

class App : Application() {





    var appComponent =  DaggerAppComponent.create()


    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance: App
            private set
    }
}