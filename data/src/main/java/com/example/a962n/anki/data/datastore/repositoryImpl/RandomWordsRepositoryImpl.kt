package com.example.a962n.anki.data.datastore.repositoryImpl

import com.example.a962n.anki.data.datastore.mapExt.instance
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.data.datastore.room.entity.RRandomWordEntity
import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.RandomWordsRepository

class RandomWordsRepositoryImpl
constructor(
    database: AppDatabase
): RandomWordsRepository {
    private val randomWordDao = database.randomWordDao()

    override fun fetchAll(): Either<Failure, List<WordEntity>> {
        val list = randomWordDao.selectBySwiped(true).map {
            WordEntity.instance(it)
        }
        return Either.Right(list)
    }

    override fun swipe(wordEntity: WordEntity): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Either<Failure, Unit> {
        randomWordDao.deleteAll()
        randomWordDao.resetSequence()
        return Either.Right(Unit)
    }

    override fun addAll(list: List<WordEntity>): Either<Failure, Unit> {
        val entities = list.map { RRandomWordEntity.instance(it) }
        randomWordDao.insertAll(entities)
        return Either.Right(Unit)
    }
}