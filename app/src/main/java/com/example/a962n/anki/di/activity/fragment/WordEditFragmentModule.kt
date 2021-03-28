package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
import com.example.a962n.anki.data.datastore.repositoryImpl.WordRepositoryImpl
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.useCase.AddWordUseCaseImpl
import com.example.a962n.presentation.wordedit.WordEditViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class WordEditFragmentModule {

    @Provides
    fun provideWordEditViewModelFactory(fragment: Fragment): WordEditViewModelFactory {
        val appDatabase = AppDatabase.create(fragment.requireContext())
        return WordEditViewModelFactory(AddWordUseCaseImpl(WordRepositoryImpl(appDatabase)))
    }


}