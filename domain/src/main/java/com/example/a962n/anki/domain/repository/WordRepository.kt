package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.entity.WordDetailEntity

interface WordRepository {
    fun fetch(word: String)
    fun delete(word: String)
    fun put(wordEntity: WordDetailEntity)
    fun fetchPronunciation(word: String)
    fun fetchMeaning(word: String)
}