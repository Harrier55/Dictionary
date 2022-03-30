package com.example.dictionary.data

import com.example.dictionary.domain.entities.skyeng.SkyengBase
import retrofit2.Call
import retrofit2.http.GET

private const val BASEURL: String = "https://dictionary.skyeng.ru/api/public/v1/words/"

/**  https://dictionary.skyeng.ru/api/public/v1/words/search?search=space&page=1&pageSize=1 */

interface SkyengService {
    @GET("search?search=space&page=1")
    fun getListTranslatedWords(): Call<MutableList<SkyengBase>>
}