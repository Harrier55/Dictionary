package com.example.dictionary.domain.words

import com.example.dictionary.domain.entities.skyeng.SkyengBase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface WordsEntityUseCase {
    fun createWord(word: WordsEntity)
    fun getListWordsFromRepo(): List<WordsEntity>
    fun requestRxToWeb(): Observable<List<SkyengBase>>
    val dataList: Single<List<WordsEntity>>
}