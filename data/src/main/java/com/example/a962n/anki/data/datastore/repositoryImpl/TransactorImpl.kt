package com.example.a962n.anki.data.datastore.repositoryImpl

import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.Transactor
import java.lang.RuntimeException

class TransactorImpl(private val database: AppDatabase) : Transactor {
    override fun <R> run(body: () -> Either<Failure, R>): Either<Failure, R> {
        var result: Either<Failure, R>? = null
        try {
            database.runInTransaction {
                result = body.invoke()
                if (result?.isLeft != false) {
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
        }
        return result ?: Either.Left(Failure.HogeFailure)
    }
}