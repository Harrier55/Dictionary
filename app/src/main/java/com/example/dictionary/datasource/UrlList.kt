package com.example.dictionary.datasource


private const val BASEURL = "https://dictionary.skyeng.ru/api/public/v1/words/"

//private const val BASEURL = "https://yandex.ru/" // error URL for test

/**  https://dictionary.skyeng.ru/api/public/v1/words/search?search=space&page=1&pageSize=1 */

object UrlList {

    fun getBaseUrl():String{
        return BASEURL
    }
}