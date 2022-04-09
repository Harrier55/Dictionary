package com.example.dictionary.di

import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.ui.MainActivityPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun provideWordsRepoImpl(): WordsRepoImpl {
        return WordsRepoImpl()
    }
}