package com.example.a962n.presentation.ankiswipe

import androidx.lifecycle.*
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.rightFlatMap
import com.example.a962n.anki.domain.core.rightMap
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.entity.WordEntity
import com.example.a962n.anki.domain.useCase.GetWordsUseCase
import com.example.a962n.anki.domain.useCase.ShuffleWordsUseCase
import com.example.a962n.anki.domain.useCase.SwipeWordUseCase

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
    data class Swiped(val target: WordEntity) : Event()
    data class Fetched(val items: List<WordEntity>) : Event()
}

@Suppress("UNCHECKED_CAST")
class AnkiSwipeViewModelFactory
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val shuffleWordsUseCase: ShuffleWordsUseCase,
    private val swipeWordUseCase: SwipeWordUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnkiSwipeViewModel(
            getWordsUseCase,
            shuffleWordsUseCase,
            swipeWordUseCase
        ) as T
    }
}

class AnkiSwipeViewModel
constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val shuffleWordsUseCase: ShuffleWordsUseCase,
    private val swipeWordUseCase: SwipeWordUseCase,
) : BaseViewModel() {

    private val _item = mutableListOf<WordEntity>()
    val item = _item.toList()

    fun fetchAll() {
        useCase(GetWordsUseCase.Param())
            .onExecute { param ->
                getWordsUseCase
                    .execute(param)
                    .rightMap { list ->
                        _item.clear()
                        _item.addAll(list)
                        list
                    }
            }
            .onSuccess {
                handleEvent(Event.Fetched(it))
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
                    }.rightMap { list ->
                        _item.clear()
                        _item.addAll(list)
                        list
                    }
            }.onFinally {
            }.onSuccess {
                handleEvent(Event.Fetched(it))
            }.onFailure {

            }.run()
    }

    fun swipe(correct: Boolean) {
        val target = _item.firstOrNull() ?: return
        _item.removeAt(0)

        useCase(SwipeWordUseCase.Param(target))
            .onExecute{
                swipeWordUseCase.execute(it).rightMap{
                    Thread.sleep(1000)
                }
            }
            .onSuccess {
                handleEvent(Event.Fetched(_item))
//                handleEvent(Event.Swiped(target))
            }.onFailure {
            }.run()
    }
}