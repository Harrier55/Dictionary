package com.example.dictionary.domain.words

interface OnWordsEntity {
    fun createWord(word: WordsEntity)
    fun readListWords(): List<WordsEntity>

    fun loadDataFromWeb()
}