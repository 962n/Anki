package com.example.a962n.anki.domain.repository

import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.failure.Failure

interface Transactor {
    fun <R> run(body: () -> Either<Failure,R>) : Either<Failure,R>
}