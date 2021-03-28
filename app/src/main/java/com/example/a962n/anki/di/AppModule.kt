package com.example.a962n.anki.di

import com.example.a962n.anki.AnkiApplication
import com.example.a962n.anki.data.datastore.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(application:AnkiApplication): AppDatabase {
        return AppDatabase.create(application)
    }

}