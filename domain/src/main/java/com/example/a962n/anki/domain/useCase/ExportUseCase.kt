package com.example.a962n.anki.domain.useCase

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.core.rightFlatMap
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.ExtWordsFileRepository
import com.example.a962n.anki.domain.repository.IndexWordsRepository
import java.util.*

interface ExportUseCase {
    class Param

    fun execute(param: Param): Either<Failure, String>
}

class ExportUseCaseImpl constructor(
    private val indexWordsRepository: IndexWordsRepository,
    private val extWordsFileRepository: ExtWordsFileRepository
) : ExportUseCase {

    override fun execute(param: ExportUseCase.Param): Either<Failure, String> {
        return indexWordsRepository
            .fetchAll()
            .rightFlatMap {
                val date = Date()
                val fileName = date.time.toString() + "_anki.csv"
                extWordsFileRepository.create(fileName, it)
            }
    }
}

