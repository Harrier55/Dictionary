package com.example.dictionary.datasource

import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

/**  https://dictionary.skyeng.ru/api/public/v1/words/search?search=space&page=1&pageSize=1 */

interface RetrofitService {
    @GET("search?search=space&page=1&pageSize=1")
    fun getListTranslatedWordsRx(): Observable<List<SkyengBase>>

    @GET("search?search=space&page=1&pageSize=1")
    fun getListTranslatedWords(): Call<List<SkyengBase>>
}