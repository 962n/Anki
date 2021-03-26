package com.example.a962n.anki.data.datastore.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity

@Dao
interface RWordDao {

    @Query("SELECT COUNT(*) > 0 FROM words WHERE name = :name")
    fun exists(name: String): Boolean

    @Insert
    fun insertAll(vararg words: RWordEntity)

    @Delete
    fun delete(word: RWordEntity)

    @Query("SELECT * FROM words ORDER BY name ASC")
    fun selectOrderNameAsc() : List<RWordEntity>
}