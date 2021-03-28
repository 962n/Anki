package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.IndexWordsRepository
import com.example.a962n.anki.domain.repository.RandomWordsRepository

interface GetWordsUseCase {
    class Param

    fun execute(param: Param): Either<Failure, List<WordEntity>>
}

class GetWordsUseCaseImpl
constructor(
    private val repository: IndexWordsRepository
) : GetWordsUseCase {
    override fun execute(param: GetWordsUseCase.Param): Either<Failure, List<WordEntity>> {
        return repository.fetchAll()

    }
}

class GetRandomWordsUseCaseImpl
constructor(
    private val repository: RandomWordsRepository
) : GetWordsUseCase {
    override fun execute(param: GetWordsUseCase.Param): Either<Failure, List<WordEntity>> {
        return repository.fetchAll()
    }
}
