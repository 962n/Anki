package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.core.rightFlatMap
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.IndexWordsRepository
import com.example.a962n.anki.domain.repository.RandomWordsRepository

interface ShuffleWordsUseCase {
    class Param
    fun execute(param: Param): Either<Failure, Unit>
}


class ShuffleWordsUseCaseImpl
constructor(
    private val indexWordsRepository: IndexWordsRepository,
    private val randomWordsRepository: RandomWordsRepository
) : ShuffleWordsUseCase {

    override fun execute(param: ShuffleWordsUseCase.Param): Either<Failure, Unit> {
        return randomWordsRepository
            .deleteAll()
            .rightFlatMap {
                indexWordsRepository.fetchAll()
            }.rightFlatMap {
                randomWordsRepository.addAll(it.shuffled())
            }
    }
}