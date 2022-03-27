package com.example.dictionary.di

import com.example.dictionary.ui.MainActivityPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class])
interface AppComponent {
    fun injectMainActivityPresenter(mainActivityPresenter: MainActivityPresenter)


}