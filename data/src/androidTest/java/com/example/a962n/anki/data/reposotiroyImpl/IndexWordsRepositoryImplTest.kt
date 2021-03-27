package com.example.a962n.anki.data.reposotiroyImpl

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a962n.anki.data.datastore.repositoryImpl.IndexWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import com.example.a962n.anki.data.roomTesting
import com.example.a962n.anki.domain.core.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class IndexWordsRepositoryImplFetchTest {

    @Test
    fun fetchEmpty() = roomTesting { database ->
        val repository = IndexWordsRepositoryImpl(database)
        val either = repository.fetchAll()
        either.onSuccess {
            Assert.assertTrue(it.isEmpty())
        }.onFailure {
            Assert.fail("do not expect this root")
        }
    }

    @Test
    fun fetchSuccess() = roomTesting { database ->
        val expectList = listOf(
            "get lost",
            "get mad at",
            "give a lecture",
            "give out",
            "had better",
            "half",
            "haunt",
            "ignorance",
            "ignore",
            "impress",
            "keep",
            "liberty",
            "lose",
            "lucky",
        )

        val shuffleList = expectList.toMutableList().shuffled()
        val insertList = shuffleList.map {
            RWordEntity(
                name = it,
                meaning = "test_meaning",
                extra = "test_extra"
            )
        }
        database.wordDao().insertAll(insertList)

        val repository = IndexWordsRepositoryImpl(database)
        repository
            .fetchAll()
            .onSuccess { actual ->
                actual.forEachIndexed { index, wordEntity ->
                    val expect = expectList[index]
                    Assert.assertTrue(expect == wordEntity.name)
                }
            }.onFailure {
                Assert.fail("do not expect this root")
            }
    }


}