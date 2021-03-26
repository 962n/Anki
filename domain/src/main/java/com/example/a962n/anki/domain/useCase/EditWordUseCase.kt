package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.WordRepository

interface EditWordUseCase {
    fun execute(wordEntity: WordEntity): Either<Failure, Unit>
}

class EditWordUseCaseImpl
constructor(
    private val wordRepository: WordRepository
) : EditWordUseCase {

    override fun execute(wordEntity: WordEntity): Either<Failure, Unit> {
        val exists = wordRepository.exists(wordEntity.name)
        if (exists) {
            return Either.Left(Failure.HogeFailure)
        }
        return wordRepository.put(wordEntity)
    }
}