package com.example.dictionary.domain.words

interface OnWords {
    fun createWord(word: Words)

    fun readListWords(): List<Words>
}