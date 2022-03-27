package com.example.dictionary.data

import android.util.Log
import com.example.dictionary.domain.words.OnWordsEntity
import com.example.dictionary.domain.words.WordsEntity

class WordsRepoImpl:OnWordsEntity {
    private var cashWords = mutableListOf<WordsEntity>()

    override fun createWord(word: WordsEntity) {

        cashWords.add(word)
    }

    override fun readListWords(): List<WordsEntity> {

        return ArrayList<WordsEntity>(cashWords)
    }

    override fun loadDataFromWeb() {
        Log.d("@@@", "readListWords: ")

    }
}