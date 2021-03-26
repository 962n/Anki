package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.PagedWordsEntity
import com.example.a962n.anki.domain.failure.Failure

interface RandomWordsRepository {
    fun fetch(nextPageKey: String?, limit: Int): Either<Failure, PagedWordsEntity>
    fun swipe(): Either<Failure, Unit>
    fun deleteAll(): Either<Failure, Unit>
    fun addAll(): Either<Failure, Unit>
}