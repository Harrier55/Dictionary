package com.example.dictionary.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASEURL = "https://dictionary.skyeng.ru/api/public/v1/words/"

//private const val BASEURL = "https://yandex.ru/" // error URL for test

/**  https://dictionary.skyeng.ru/api/public/v1/words/search?search=space&page=1&pageSize=1 */

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }
}