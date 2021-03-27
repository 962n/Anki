package com.example.a962n.anki.data.datastore.repositoryImpl

import com.example.a962n.anki.data.datastore.mapExt.instance
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.IndexWordsRepository

class IndexWordsRepositoryImpl
constructor(
    private val database: AppDatabase
) : IndexWordsRepository {

    override fun fetchAll(): Either<Failure, List<WordEntity>> {
        val list = database.wordDao()
            .selectOrderNameAsc()
            .map {
                WordEntity.instance(it)
            }
        val nextPageKey = list.lastOrNull()?.name
        return Either.Right(list)
    }
}