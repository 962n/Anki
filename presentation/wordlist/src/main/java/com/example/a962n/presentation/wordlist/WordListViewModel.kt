package com.example.a962n.presentation.wordlist

import androidx.lifecycle.*
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.rightMap
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.useCase.DeleteWordUseCase
import com.example.a962n.anki.domain.useCase.GetWordsUseCase

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
    data class Loading(val isLoading: Boolean) : Event()
    data class FetchFailed(val failure: Failure) : Event()
    data class DeletedItem(val entity: WordEntity) : Event()
}

@Suppress("UNCHECKED_CAST")
class WordListViewModelFactory
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WordListViewModel(getWordsUseCase, deleteWordUseCase) as T
    }
}

class WordListViewModel
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
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

    fun deleteItem(item: WordEntity) {
        useCase(DeleteWordUseCase.Param(item.id, item.name, item.meaning, item.extra))
            .onExecute{ param ->
                deleteWordUseCase
                    .execute(param)
                    .rightMap {
                        _items.value?.removeAll { it.id == item.id }
                        Unit
                    }
            }
            .onSuccess {
                handleEvent(Event.DeletedItem(item))
            }.onFailure {
                handleEvent(Event.FetchFailed(Failure.HogeFailure))
            }.run()

    }

    private fun handleItems(list: List<WordEntity>) {
        this._items.value = list.toMutableList()
    }
}