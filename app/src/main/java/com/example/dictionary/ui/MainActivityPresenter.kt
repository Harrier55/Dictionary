package com.example.dictionary.ui

import com.example.dictionary.App
import com.example.dictionary.datasource.OnCallbackWebRequest
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.datasource.WebConnection
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "@@@"

class MainActivityPresenter(private val mainActivity: MainActivity) :
    MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = null


    @Inject
    lateinit var wordsRepoImpl: WordsRepoImpl

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

    override fun requestWordTranslation(searchWord: String) {
        mainActivity.startShowProgressLoading()
        /**тест проверки для связи c с адаптером*/
//        mainActivity.showListTranslated(mockList)
        /** Обычный запрос через callback**/
//        WebConnection(onCallbackWebRequest).webRequest()


        /**Rx запрос*/
        wordsRepoImpl.requestToWeb()
            .  subscribeOn(Schedulers.io())
            . observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it != null) {
                  convertDataToRepository(it)
                    val list = wordsRepoImpl.getListWordsFromRepo()
                    mainActivity.showListWordsTranslated(list)
                    mainActivity.stopShowProgressLoading()
                }
            }
            .doOnError {

            }
            .doOnComplete {

            }
            .subscribe()
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
        mainActivity.stopShowProgressLoading()

/** Rx **/
//        wordsRepoImpl.dataList
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onSuccess = {
//                    mainActivity.showListWordsTranslated(it)
//                    mainActivity.dismissProgressDialog()
//                },
//                onError = { it.localizedMessage }
//            )
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