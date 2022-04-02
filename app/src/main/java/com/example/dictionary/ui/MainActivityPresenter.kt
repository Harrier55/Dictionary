package com.example.dictionary.ui

import android.util.Log
import com.example.dictionary.App
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.domain.words.WordsEntity
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivityPresenter:MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = null
    private val mainActivity by lazy { MainActivity() }

    @Inject
    lateinit var wordsRepoImpl: WordsRepoImpl

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
      wordsRepoImpl.loadDataFromWeb()
    }

    fun loadData(list:List<WordsEntity>){
        Log.d("@@@", "loadData: OK$list")
        mainActivity.showListTranslated(list)
    }
}