package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.AddWordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.WordRepository

interface EditWordUseCase {

    data class Param(val name: String?, val meaning: String?, val extra: String?) {
        internal fun toAddWordEntity(): AddWordEntity {
            val name = this.name ?: ""
            val meaning = this.meaning ?: ""
            val extra = this.extra ?: ""
            return AddWordEntity(
                name,
                meaning,
                extra
            )
        }
    }

    fun execute(param: Param): Either<Failure, Unit>
}

class AddWordUseCaseImpl
constructor(
    private val wordRepository: WordRepository
) : EditWordUseCase {

    override fun execute(param: EditWordUseCase.Param): Either<Failure, Unit> {
        val name = param.name ?: return Either.Left(Failure.HogeFailure)
        val exists = wordRepository.exists(name)
        if (exists) {
            return Either.Left(Failure.HogeFailure)
        }
        return wordRepository.add(param.toAddWordEntity())
    }
}