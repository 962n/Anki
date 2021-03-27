package com.example.a962n.anki.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.a962n.anki.data.datastore.room.AppDatabase


fun roomTesting(func : (database:AppDatabase)->Unit) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val database = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
    func(database)
    database.clearAllTables()
}