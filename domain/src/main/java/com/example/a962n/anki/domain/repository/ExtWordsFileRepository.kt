package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure

interface ExtWordsFileRepository {
    fun fetch(filePath: String): Either<Failure, List<WordEntity>>
    fun create(filePath: String, words: List<WordEntity>): Either<Failure, String>
}