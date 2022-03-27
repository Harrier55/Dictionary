package com.example.dictionary.ui

import android.util.Log
import com.example.dictionary.App
import com.example.dictionary.data.SkyengService
import com.example.dictionary.data.WordsRepoImpl
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivityPresenter : MainActivityContract.MainActivityPresenter {
    private var view: MainActivityContract.MainActivityView? = null

    @Inject
    lateinit var wordsRepoImpl: WordsRepoImpl

    @Inject
    lateinit var retrofit: Retrofit

    init {
        App.instance.appComponent.injectMainActivityPresenter(this)
    }


    override fun attachView(view: MainActivityContract.MainActivityView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getListWordTranslated(searchWord: String) {
        view?.startShowProgressLoading()
        foo()
    }

    fun foo() {
        val ret = retrofit.create(SkyengService::class.java).getListTranslatedWords()
        Log.d("@@@", "foo: "+ ret)
    }
}