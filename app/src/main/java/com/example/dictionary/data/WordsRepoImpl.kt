package com.example.dictionary.data

import android.util.Log
import com.example.dictionary.App
import com.example.dictionary.datasource.RetrofitService
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import com.example.dictionary.domain.words.WordsEntityUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

private const val TAG = "@@@"

class WordsRepoImpl() : WordsEntityUseCase {
    private var cashWords = mutableListOf<WordsEntity>()

    @Inject
    lateinit var retrofit: Retrofit


    init {
        App.instance.appComponent.injectWordRepoImpl(this)
    }

    override fun createWord(word: WordsEntity) {
        cashWords.add(word)
    }

    override fun getListWordsFromRepo(): List<WordsEntity> {
        return cashWords
    }

    override val dataList: Single<List<WordsEntity>>
        get() = Single.just(cashWords)


    /** завернул запрос в Сеть в Observer
     * статья на Хабое Исследуем RxLava 2 для Андроид**/

    override fun requestRxToWeb(): Observable<List<SkyengBase>> {

        val call = retrofit.create(RetrofitService::class.java).getListTranslatedWords()

        return Observable.create<List<SkyengBase>> {

            call.enqueue(object : Callback<List<SkyengBase>> {
                override fun onResponse(
                    call: Call<List<SkyengBase>>,
                    response: Response<List<SkyengBase>>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        it.onNext(response.body())
                    }
                    it.onComplete()
                }

                override fun onFailure(call: Call<List<SkyengBase>>, t: Throwable) {
                    Log.d("@@@", "fun requestRxToWeb()__onFailure: ${t.message}")
                    it.onError(t)
                }
            })
        }
    }


}