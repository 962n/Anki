package com.example.a962n.presentation.ankiswipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.useCase.GetWordsUseCase
import com.example.a962n.anki.domain.useCase.ShuffleWordsUseCase

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
}

@Suppress("UNCHECKED_CAST")
class AnkiSwipeViewModelFactory
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val shuffleWordsUseCase: ShuffleWordsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnkiSwipeViewModel(getWordsUseCase, shuffleWordsUseCase) as T
    }
}

class AnkiSwipeViewModel
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val shuffleWordsUseCase: ShuffleWordsUseCase
) : BaseViewModel() {

}