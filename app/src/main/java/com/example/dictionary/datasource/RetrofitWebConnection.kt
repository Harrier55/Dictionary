package com.example.dictionary.datasource


import android.util.Log
import com.example.dictionary.data.OnCallbackWebRequest
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASEURL = "https://dictionary.skyeng.ru/api/public/v1/words/"

/**  https://dictionary.skyeng.ru/api/public/v1/words/search?search=space&page=1&pageSize=1 */

class RetrofitWebConnection(private val onCallbackWebRequest: OnCallbackWebRequest) {

    private val retrofit = provideRetrofit()

     fun webRequest(){
       val retrofitService = retrofit.create(RetrofitService1::class.java)
        retrofitService.getListTranslatedWords().enqueue(object :Callback<List<SkyengBase>>{
            override fun onResponse(
                call: Call<List<SkyengBase>>,
                response: Response<List<SkyengBase>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val skyEngBase = response.body()
                    Log.d("@@@", "onResponse: RetrofitWebConnection" + skyEngBase.toString())
                }
                onCallbackWebRequest.onResponse(response.body())
            }

            override fun onFailure(call: Call<List<SkyengBase>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }

}

interface RetrofitService1 {

    @GET("search?search=space&page=1&pageSize=1")
    fun getListTranslatedWords(): Call<List<SkyengBase>>
}