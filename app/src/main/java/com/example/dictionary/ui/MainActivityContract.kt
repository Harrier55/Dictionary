package com.example.dictionary.ui

import android.view.View
import com.example.dictionary.domain.words.WordsEntity

class MainActivityContract {

    interface MainActivityView{
        fun showListWordsTranslated(list:List<WordsEntity>)
        fun showError()
        fun startShowProgressLoading()
        fun stopShowProgressLoading()

    }

    interface MainActivityPresenter{
        fun attachView(view: MainActivityView)
        fun detachView()
        fun requestWordTranslation(searchWord: String)
        fun loadDataFromRepo()
    }

}