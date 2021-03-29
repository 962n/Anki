package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.RandomWordsRepository

interface SwipeWordUseCase {
    data class Param(val entity: WordEntity)

    fun execute(param: Param): Either<Failure, Unit>
}

class SwipeWordUseCaseImpl
constructor(
    private val repository: RandomWordsRepository
) : SwipeWordUseCase {

    override fun execute(param: SwipeWordUseCase.Param): Either<Failure, Unit> {
        return repository.swipe(param.entity)
    }
}
