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
    object SwipeFailed : Event()
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

    private var _item = emptyList<WordEntity>()

    fun fetchAll() {
        useCase(GetWordsUseCase.Param())
            .onExecute { param ->
                getWordsUseCase
                    .execute(param)
            }
            .onSuccess {
                _item = it
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
                    }
            }.onFinally {
            }.onSuccess {
                _item = it
                handleEvent(Event.Fetched(it))
            }.onFailure {

            }.run()
    }

    fun swipe(position :Int,correct: Boolean) {
        if (position > _item.size - 1) {
            return
        }
        val target = _item[position]
        useCase(SwipeWordUseCase.Param(target))
            .onExecute {
                swipeWordUseCase.execute(it)
            }
            .onSuccess {
//                handleEvent(Event.Fetched(_item))
//                handleEvent(Event.Swiped(target))
            }.onFailure {
                handleEvent(Event.SwipeFailed)
            }.run()
    }
}