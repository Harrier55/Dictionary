package com.example.dictionary.data.retrofitApi

import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RetrofitService {
    @GET("search?search=space&page=1&pageSize=1")
    fun getListTranslatedWordsRx(): Observable<List<SkyengBase>>
}