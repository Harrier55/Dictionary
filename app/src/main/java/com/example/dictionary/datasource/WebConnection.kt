package com.example.dictionary.datasource


import android.util.Log
import com.example.dictionary.App
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class WebConnection(private val onCallbackWebRequest: OnCallbackWebRequest) {

    @Inject lateinit var retrofit: Retrofit

    init {
        App.instance.appComponent.injectWebConnection(this)
    }

     fun webRequest(){
       val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.getListTranslatedWords(word = "cat").enqueue(object :Callback<List<SkyengBase>>{
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

}

