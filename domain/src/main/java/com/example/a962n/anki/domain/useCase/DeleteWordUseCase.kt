package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.WordRepository

interface DeleteWordUseCase {
    data class Param(
        val wordId: Int,
        val name: String,
        val meaning: String,
        val extra: String
    ) {
        fun toWordEntity(): WordEntity {
            return WordEntity(wordId, name, meaning, extra)
        }
    }

    fun execute(param: Param): Either<Failure, Unit>

}

class DeleteWordUseCaseImpl
constructor(
    private val repository: WordRepository
) : DeleteWordUseCase {
    override fun execute(param: DeleteWordUseCase.Param): Either<Failure, Unit> {
        return repository.delete(param.toWordEntity())
    }
}