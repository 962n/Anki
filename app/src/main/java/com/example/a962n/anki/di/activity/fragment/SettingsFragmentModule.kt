package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
import com.example.a962n.anki.data.datastore.repositoryImpl.ExtWordsFileRepositoryImpl
import com.example.a962n.anki.data.datastore.repositoryImpl.IndexWordsRepositoryImpl
import com.example.a962n.anki.data.datastore.room.AppDatabase
import com.example.a962n.anki.domain.useCase.ExportUseCaseImpl
import com.example.a962n.anki.navigatorImpl.SettingsNavigatorImpl
import com.example.a962n.presentation.ankiswipe.AnkiSwipeViewModelFactory
import com.example.a962n.presentation.settings.SettingsNavigator
import com.example.a962n.presentation.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class SettingsFragmentModule {

    @Provides
    fun provideSettingsViewModelFactory(fragment: Fragment): SettingsViewModelFactory {
        val context = fragment.requireContext()
        val database = AppDatabase.create(context)
        val extWordsFileRepository = ExtWordsFileRepositoryImpl(context)
        val indexWordsRepository = IndexWordsRepositoryImpl(database)
        return SettingsViewModelFactory(
            ExportUseCaseImpl(indexWordsRepository, extWordsFileRepository)
        )
    }

    @Provides
    fun provideSettingsNavigator(fragment: Fragment): SettingsNavigator {
        return SettingsNavigatorImpl()
    }


}