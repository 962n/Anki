package com.example.a962n.presentation.wordlist

import androidx.lifecycle.*
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.useCase.GetWordsUseCase

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
    data class Loading(val isLoading: Boolean) : Event()
    data class FetchFailed(val failure: Failure) : Event()
}

@Suppress("UNCHECKED_CAST")
class WordListViewModelFactory
constructor(
    private val getWordsUseCase: GetWordsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WordListViewModel(getWordsUseCase) as T
    }
}

class WordListViewModel
constructor(
    private val getWordsUseCase: GetWordsUseCase
) : BaseViewModel() {

    private val _items = MutableLiveData<MutableList<WordEntity>>()
    val items = _items.map {
        it.toList()
    }

    fun fetchAll() {
        handleEvent(Event.Loading(true))
        useCase(GetWordsUseCase.Param())
            .onExecute(getWordsUseCase::execute)
            .onFinally {
                handleEvent(Event.Loading(false))
            }
            .onSuccess {
                handleItems(it)
            }
            .onFailure {
                handleEvent(Event.FetchFailed(Failure.HogeFailure))
            }.run()
    }

    private fun handleItems(list: List<WordEntity>) {
        this._items.value = list.toMutableList()
    }
}