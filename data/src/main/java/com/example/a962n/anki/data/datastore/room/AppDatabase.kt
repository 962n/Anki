package com.example.a962n.anki.data.datastore.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.a962n.anki.data.datastore.room.dao.RWordDao
import com.example.a962n.anki.data.datastore.room.entity.RRandomWordEntity
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity

@Database(
    entities = [
        RWordEntity::class,
        RRandomWordEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): RWordDao
}