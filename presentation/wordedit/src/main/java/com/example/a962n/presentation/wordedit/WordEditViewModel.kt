package com.example.a962n.presentation.wordedit

import androidx.lifecycle.MutableLiveData
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.failure.Failure
import com.example.a962n.anki.domain.useCase.EditWordUseCase

sealed class Event : BaseViewModel.ViewModelEvent.FeatureViewModelEvent() {
    object EditSuccess : Event()
    data class EditFailed(val failure: Failure) : Event()
}

//@HiltViewModel
internal class WordEditViewModel
constructor(
    private val editWordUseCase: EditWordUseCase
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