package com.example.dictionary.data

import android.util.Log
import com.example.dictionary.App
import com.example.dictionary.data.retrofitApi.RetrofitService
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.OnWordsEntity
import com.example.dictionary.domain.words.WordsEntity
import com.example.dictionary.ui.MainActivityPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class WordsRepoImpl : OnWordsEntity {
    private var cashWords = mutableListOf<WordsEntity>()
    private val presenter by lazy { MainActivityPresenter() }

    init {
        App.instance.appComponent.injectWordRepoImpl(this)
    }

    @Inject
    lateinit var retrofit: Retrofit

    override fun createWord(word: WordsEntity) {
        cashWords.add(word)
    }

    override fun readListWords(): List<WordsEntity> {

        return ArrayList<WordsEntity>(cashWords)
    }

    override fun loadDataFromWeb() {


        val messagesApi = retrofit.create(RetrofitService::class.java)

        Log.d("@", "loadDataFromWeb: ")

        messagesApi.getListTranslatedWordsRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { Log.d("@@@", "myRxRetrofitError: ${it.localizedMessage}") },
                onSuccess = {
                    Log.d("@@@", "myRxRetrofit: $it")
                    convertForRepository(it)
                    Log.d("@@@", "myRxRetrofit: stop")
                }
            )

    }

    override fun convertForRepository(listSkyEng: List<SkyengBase>) {
        listSkyEng.forEach { it ->
            it.meanings.forEach {
                createWord(
                    word = WordsEntity(
                        it.translation.text,
                        it.transcription
                    )
                )

            }
        }
        Log.d("@@@", "convertForRepository: stop"  )
        presenter.loadData(cashWords)
    }



}