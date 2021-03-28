package com.example.a962n.anki.data.datastore.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a962n.anki.data.datastore.room.dao.RRandomWordDao
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
    companion object {
        private const val DB_NAME = "hoge"
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun randomWordDao(): RRandomWordDao

    abstract fun wordDao(): RWordDao
}