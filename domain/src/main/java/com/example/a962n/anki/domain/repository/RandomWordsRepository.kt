package com.example.a962n.anki.domain.repository

interface RandomWordsRepository {
    fun fetch()
    fun check()
    fun shuffle()
}