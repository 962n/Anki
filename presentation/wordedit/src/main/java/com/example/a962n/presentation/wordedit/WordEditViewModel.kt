package com.example.a962n.presentation.wordedit

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.useCase.EditWordUseCase
import javax.inject.Inject

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
    object EditSuccess : Event()
    data class EditFailed(val failure: Failure) : Event()
}

@Suppress("UNCHECKED_CAST")
class WordEditViewModelFactory
@Inject
constructor(
    private val editWordUseCase: EditWordUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WordEditViewModel(editWordUseCase) as T
    }
}

class WordEditViewModel
@ViewModelInject
constructor(
    private val editWordUseCase: EditWordUseCase,
//    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _textName = MutableLiveData<String>()
    val textName: MutableLiveData<String> = _textName

    private val _textMeaning = MutableLiveData<String>()
    val textMeaning: MutableLiveData<String> = _textMeaning

    private val _textExtra = MutableLiveData<String>()
    val textExtra: MutableLiveData<String> = _textExtra


    fun editWord() {

        val param = EditWordUseCase.Param(
            _textName.value,
            _textMeaning.value,
            _textExtra.value
        )
        useCase(param)
            .onExecute(editWordUseCase::execute)
            .onSuccess {
                handleEvent(Event.EditSuccess)
            }.onFailure {
                handleEvent(Event.EditFailed(it))
            }
    }
}