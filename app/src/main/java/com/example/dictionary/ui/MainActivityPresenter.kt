package com.example.dictionary.ui

import android.util.Log
import android.widget.Toast
import com.example.dictionary.App
import com.example.dictionary.data.Error
import com.example.dictionary.datasource.OnCallbackWebRequest
import com.example.dictionary.data.WordsRepoImpl
import com.example.dictionary.domain.entities.skyeng.SkyengBase
import com.example.dictionary.domain.words.WordsEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

private const val TAG = "@@@"

class MainActivityPresenter(private val mainActivity: MainActivity) :
    MainActivityContract.MainActivityPresenter {

    private var view: MainActivityContract.MainActivityView? = null
    private var disposable: Disposable? = null

    @Inject
    lateinit var wordsRepoImpl: WordsRepoImpl
    @Inject
    lateinit var myErrorClass: Error

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
        disposable?.dispose()
    }

    override fun requestWordTranslation(searchWord: String) {
        mainActivity.showProgressLoading(true)
        /**тест проверки для связи c с адаптером*/
//        mainActivity.showListTranslated(mockList)
        /** Обычный запрос через callback: OnCallbackWebRequest  **/
//        WebConnection(onCallbackWebRequest).webRequest()
        /**Rx запрос*/
        disposable = wordsRepoImpl.requestRxToWeb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                try {
                    if (it != null) {
                        convertDataToRepository(it)
                        val list = wordsRepoImpl.getListWordsFromRepo()
                        mainActivity.showListWordsTranslated(list)
                        mainActivity.showProgressLoading(false)
                    }
                } catch (e: HttpRetryException) {
                    Log.d(TAG, "requestWordTranslation: ${e.message.toString()}")
                }

            }
            .onErrorResumeNext {
                Log.d(TAG, "requestWordTranslation: " + it.localizedMessage)
                myErrorClass.error = it
                return@onErrorResumeNext ObservableSource {
                    mainActivity.showProgressLoading(false)
                    mainActivity.showError(myErrorClass)
                }
            }
            .doOnError {
                Log.d(TAG, "requestWordTranslation: doOnError ${it.localizedMessage}")

                /** здесь была проблема - при любой ошибке приложение падало, **/
                /** пока не внедрил метод   onErrorResumeNext**/
            }
            .doOnComplete {
                mainActivity.showProgressLoading(false)
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
        mainActivity.showProgressLoading(false)

        /**  получение списка из репозитория через Rx **/
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