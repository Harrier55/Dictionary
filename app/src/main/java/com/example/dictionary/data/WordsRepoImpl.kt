package com.example.dictionary.data

import com.example.dictionary.domain.words.OnWords
import com.example.dictionary.domain.words.Words

class WordsRepoImpl:OnWords {
    private var cashWords = mutableListOf<Words>()

    override fun createWord(word: Words) {
        cashWords.add(word)
    }

    override fun readListWords(): List<Words> {
        return ArrayList<Words>(cashWords)
    }
}