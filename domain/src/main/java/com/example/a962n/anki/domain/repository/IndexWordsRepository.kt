package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.PagedWordsEntity
import com.example.a962n.anki.domain.failure.Failure

interface IndexWordsRepository {
    fun fetch(nextPageKey: String?, limit: Int): Either<Failure, PagedWordsEntity>
}
