package com.example.a962n.anki.di.activity.fragment

import androidx.fragment.app.Fragment
import com.example.a962n.anki.navigatorImpl.WordListNavigatorImpl
import com.example.a962n.presentation.wordlist.WordListNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class WordListFragmentModule {

    @Provides
    fun provideNavigator(fragment: Fragment): WordListNavigator {
        return WordListNavigatorImpl(fragment)
    }

}