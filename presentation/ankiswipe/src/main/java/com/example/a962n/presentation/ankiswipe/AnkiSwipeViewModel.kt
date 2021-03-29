package com.example.a962n.presentation.ankiswipe

import androidx.lifecycle.*
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.rightFlatMap
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.entity.WordEntity
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

    private val _item = MutableLiveData<List<WordEntity>>()
    val item: LiveData<List<WordEntity>> = _item

    fun fetchAll() {
        useCase(GetWordsUseCase.Param())
            .onExecute(getWordsUseCase::execute)
            .onSuccess {
                _item.value = it
            }.onFailure {
                // do nothing
            }.run()
    }

    fun shuffle() {
        useCase(ShuffleWordsUseCase.Param())
            .onExecute {
                shuffleWordsUseCase
                    .execute(it)
                    .rightFlatMap {
                        getWordsUseCase.execute(GetWordsUseCase.Param())
                    }
            }.onFinally {
            }.onSuccess {
                _item.value = it
            }.onFailure {

            }.run()
    }

    fun swipe(correct: Boolean) {

    }


}