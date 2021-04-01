package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
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
        return SettingsViewModelFactory()
    }

    @Provides
    fun provideSettingsNavigator(fragment: Fragment): SettingsNavigator {
        return SettingsNavigatorImpl()
    }


}