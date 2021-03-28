package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
import com.example.a962n.anki.data.datastore.repositoryImpl.IndexWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.repositoryImpl.WordRepositoryImpl
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.repository.WordRepository
import com.example.a962n.anki.domain.useCase.AddWordUseCaseImpl
import com.example.a962n.anki.domain.useCase.EditWordUseCase
import com.example.a962n.anki.domain.useCase.GetWordsUseCase
import com.example.a962n.anki.domain.useCase.GetWordsUseCaseImpl
import com.example.a962n.anki.navigatorImpl.WordListNavigatorImpl
import com.example.a962n.presentation.wordlist.WordListNavigator
import com.example.a962n.presentation.wordlist.WordListViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class WordListFragmentModule {

    @Provides
    fun provideWordListNavigator(fragment: Fragment): WordListNavigator {
        return WordListNavigatorImpl(fragment)
    }

    @Provides
    fun provideWordListViewModelFactory(fragment: Fragment): WordListViewModelFactory {
        val appDatabase = AppDatabase.create(fragment.requireContext())
        val getWordsUseCase = GetWordsUseCaseImpl(IndexWordsRepositoryImpl(appDatabase))
        return WordListViewModelFactory(getWordsUseCase)
    }


}