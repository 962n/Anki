package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure

interface WordRepository {
    fun exists(word: String) : Boolean
    fun delete(wordEntity: WordEntity) : Either<Failure, Unit>
    fun put(wordEntity: WordEntity) : Either<Failure, Unit>
    fun fetchPronunciation(word: String) : Either<Failure, Unit>
    fun fetchMeaning(word: String) : Either<Failure, Unit>
}