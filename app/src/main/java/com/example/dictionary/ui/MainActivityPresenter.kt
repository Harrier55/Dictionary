package com.example.dictionary.ui

class MainActivityPresenter:MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = null

    override fun attachView(view: MainActivityContract.MainActivityView) {
       this.view = view
    }

    override fun detachView() {
       this.view = null
    }

    override fun getListWordTranslated(searchWord: String) {
      //  TODO("Not yet implemented")
    }
}