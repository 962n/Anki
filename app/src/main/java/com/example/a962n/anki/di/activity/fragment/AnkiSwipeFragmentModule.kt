package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
import com.example.a962n.anki.data.datastore.repositoryImpl.IndexWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.repositoryImpl.RandomWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.useCase.GetRandomWordsUseCaseImpl
import com.example.a962n.anki.domain.useCase.ShuffleWordsUseCaseImpl
import com.example.a962n.presentation.ankiswipe.AnkiSwipeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class AnkiSwipeFragmentModule {

    @Provides
    fun provideAnkiSwipeViewModelFactory(fragment: Fragment): AnkiSwipeViewModelFactory {
        val appDatabase = AppDatabase.create(fragment.requireContext())
        val indexWordsRepository = IndexWordsRepositoryImpl(appDatabase)
        val randomWordsRepository = RandomWordsRepositoryImpl(appDatabase)
        val getWordUseCase = GetRandomWordsUseCaseImpl(randomWordsRepository)
        val shuffleWordsUseCase = ShuffleWordsUseCaseImpl(
            indexWordsRepository,
            randomWordsRepository
        )
        return AnkiSwipeViewModelFactory(getWordUseCase, shuffleWordsUseCase)
    }


}