package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure

interface IndexWordsRepository {
    fun fetchAll(): Either<Failure, List<WordEntity>>
}
