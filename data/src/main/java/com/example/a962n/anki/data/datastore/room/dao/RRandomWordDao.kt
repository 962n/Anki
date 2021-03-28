package com.example.a962n.anki.data.datastore.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a962n.anki.data.datastore.room.entity.RRandomWordEntity
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity

@Dao
interface RRandomWordDao {

    @Query("DELETE FROM random_words")
    fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'random_words'")
    fun resetSequence()

    @Insert
    fun insertAll(vararg words: RRandomWordEntity): List<Long>

    @Insert
    fun insertAll(words: List<RRandomWordEntity>): List<Long>

    @Query("""
        SELECT * FROM words 
        INNER JOIN random_words ON words.id = random_words.word_id
        WHERE random_words.swiped = :swiped
    """)
    fun selectBySwiped(swiped:Boolean) : List<RWordEntity>


}