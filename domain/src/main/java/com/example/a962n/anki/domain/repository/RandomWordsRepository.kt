package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure

interface RandomWordsRepository {
    fun fetchAll(): Either<Failure, List<WordEntity>>
    fun swipe(wordEntity: WordEntity): Either<Failure, Unit>
    fun deleteAll(): Either<Failure, Unit>
    fun addAll(list: List<WordEntity>): Either<Failure, Unit>
}