package com.example.a962n.anki.data.datastore.repositoryImpl

import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import com.example.a962n.anki.domain.core.Either
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.repository.WordRepository

class WordRepositoryImpl(database: AppDatabase) : WordRepository {
    private val wordDao = database.wordDao()

    override fun exists(word: String): Boolean {
        return wordDao.exists(word)
    }

    override fun delete(wordEntity: WordEntity): Either<Failure, Unit> {
        val rWordEntity = RWordEntity.instance(wordEntity)
        wordDao.delete(rWordEntity)
        return Either.Right(Unit)
    }

    override fun put(wordEntity: WordEntity): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    override fun fetchPronunciation(word: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    override fun fetchMeaning(word: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }
}