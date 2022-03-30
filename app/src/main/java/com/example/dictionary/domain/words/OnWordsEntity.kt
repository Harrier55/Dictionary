package com.example.dictionary.domain.words

import com.example.dictionary.domain.entities.skyeng.SkyengBase

interface OnWordsEntity {
    fun createWord(word: WordsEntity)
    fun readListWords(): List<WordsEntity>
    fun loadDataFromWeb()
    fun convertForRepository(listSkyEng:List<SkyengBase>)
}