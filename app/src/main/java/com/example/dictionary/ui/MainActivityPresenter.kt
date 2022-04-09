package com.example.dictionary.ui

import com.example.dictionary.App
import com.example.dictionary.datasource.OnCallbackWebRequest
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.datasource.WebConnection
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import javax.inject.Inject

private const val TAG = "@@@"

class MainActivityPresenter(private val mainActivity: MainActivity) :
    MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = null


    @Inject lateinit var wordsRepoImpl: WordsRepoImpl

    init {
        App.instance.appComponent.injectMainActivityPresenter(this)
    }


    private val mockList = listOf<WordsEntity>(
        WordsEntity("Text", "Translate"),
        WordsEntity("Text", "Translate"),
        WordsEntity("Text", "Translate"),
        WordsEntity("Text", "Translate")
    )

    override fun attachView(view: MainActivityContract.MainActivityView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun requestTranslated(searchWord: String) {
//        mainActivity.showListTranslated(mockList)   // тест проверки для связи
        loadDataFromWeb()
        mainActivity.showProgressDialog()
    }

    private fun loadDataFromWeb() {
        WebConnection(onCallbackWebRequest).webRequest()
    }

    private val onCallbackWebRequest = object : OnCallbackWebRequest {
        override fun onResponse(body: List<SkyengBase>?) {
            if (body != null) {
                convertDataToRepository(body)
                loadDataFromRepo()
            }
        }

        override fun onFailure() {
            TODO("Not yet implemented")
        }
    }

    override fun loadDataFromRepo() {
        val list = wordsRepoImpl.getListWordsFromRepo()
        mainActivity.showListWordsTranslated(list)
        mainActivity.dismissProgressDialog()


//        wordsRepoImpl.dataList
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {mainActivity.showListTranslated(it)},
//                onError = {it.localizedMessage}
//        )
    }

    private fun convertDataToRepository(listSkyEng: List<SkyengBase>) {
        listSkyEng.forEach { it ->
            it.meanings.forEach {
                wordsRepoImpl.createWord(
                    word = WordsEntity(
                        it.translation.text,
                        it.transcription
                    )
                )
            }
        }
    }


}