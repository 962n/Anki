package com.example.a962n.anki.data.reposotiroyImpl

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a962n.anki.data.datastore.mapExt.instance
import com.example.a962n.anki.data.datastore.repositoryImpl.WordRepositoryImpl
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import com.example.a962n.anki.data.roomTesting
import com.example.a962n.anki.domain.core.onFailure
import com.example.a962n.anki.domain.core.onSuccess
import com.example.a962n.anki.domain.entity.WordEntity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordRepositoryImplExistsTest {

    @Test
    fun exists() = roomTesting { database ->
        val name = "test_name"
        val entity = RWordEntity(
            name = name,
            meaning = "test_meaning",
            extra = "test_extra"
        )
        database.wordDao().insertAll(entity)

        val repository = WordRepositoryImpl(database)

        val exists = repository.exists(name)
        Assert.assertTrue(exists)
    }

    @Test
    fun notExists() = roomTesting { database ->
        val name = "test_name"
        val repository = WordRepositoryImpl(database)
        val exists = repository.exists(name)
        Assert.assertFalse(exists)
    }

}

@RunWith(AndroidJUnit4::class)
class WordRepositoryImplDeleteTest {

    @Test
    fun deleteSuccess() = roomTesting { database ->
        val rEntity = RWordEntity(
            name = "test_name",
            meaning = "test_meaning",
            extra = "test_extra"
        )
        database.wordDao().insertAll(rEntity)

        val repository = WordRepositoryImpl(database)
        val entity = WordEntity.instance(rEntity)
        repository.delete(entity)
            .onSuccess {
                Assert.assertTrue(true)
            }.onFailure {
                Assert.fail("do not expect calling this callback")
            }
        val exists = repository.exists(rEntity.name)
        Assert.assertTrue(exists)
    }

}

//@RunWith(AndroidJUnit4::class)
//class WordRepositoryImplAddTest {
//
//    @Test
//    fun addSuccess() = roomTesting {
//
//
//
//    }
//
//
//
//}
