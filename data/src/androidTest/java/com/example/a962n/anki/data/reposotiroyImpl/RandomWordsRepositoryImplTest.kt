package com.example.a962n.anki.data.reposotiroyImpl

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a962n.anki.data.datastore.repositoryImpl.RandomWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.room.entity.RRandomWordEntity
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import com.example.a962n.anki.data.roomTesting
import com.example.a962n.anki.domain.core.onFailure
import com.example.a962n.anki.domain.core.onSuccess
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomWordsRepositoryImplFetchAllTest {


    @Test
    fun successEmpty() = roomTesting { database ->
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
        val selectedList = database.wordDao().selectOrderNameAsc()
        val insertRandom = selectedList.map {
            RRandomWordEntity(
                wordId = it.id,
                swiped = true
            )
        }
        database.randomWordDao().insertAll(insertRandom)

        val repository = RandomWordsRepositoryImpl(database)
        val either = repository.fetchAll()
        either.onSuccess {
            Assert.assertTrue(it.isEmpty())
        }.onFailure {
            Assert.fail("do not expect this root")
        }

    }

    @Test
    fun success() = roomTesting { database ->
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
        val selectedList = database.wordDao().selectOrderNameAsc()
        val insertRandom = selectedList.map {
            RRandomWordEntity(
                wordId = it.id,
                swiped = false
            )
        }
        database.randomWordDao().insertAll(insertRandom)

        val repository = RandomWordsRepositoryImpl(database)
        val either = repository.fetchAll()
        either.onSuccess {
            Assert.assertEquals(expectList.size, it.size)
        }.onFailure {
            Assert.fail("do not expect this root")
        }

    }


}

@RunWith(AndroidJUnit4::class)
class RandomWordsRepositoryImplDeleteAllTest {

    @Test
    fun success() = roomTesting { database ->
        val expectList = listOf("a","b","c","d","e")

        val shuffleList = expectList.toMutableList().shuffled()
        val insertList = shuffleList.map {
            RWordEntity(
                name = it,
                meaning = "test_meaning",
                extra = "test_extra"
            )
        }
        database.wordDao().insertAll(insertList)
        val selectedList = database.wordDao().selectOrderNameAsc()
        val insertRandom = selectedList.map {
            RRandomWordEntity(
                wordId = it.id,
                swiped = true
            )
        }
        database.randomWordDao().insertAll(insertRandom)
        val repository = RandomWordsRepositoryImpl(database)
        val either = repository.deleteAll()
        either.onFailure {
            Assert.fail("do not expect this root")
        }
        val rRandom = RRandomWordEntity(
            wordId = selectedList[0].id,
            swiped = false
        )
        database.randomWordDao().insertAll(rRandom)
        val actual = database.randomWordDao().selectAll()
        Assert.assertEquals(actual[0].id,1)
    }
}