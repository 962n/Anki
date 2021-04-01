package com.example.a962n.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel
import com.example.a962n.anki.domain.core.useCase
import com.example.a962n.anki.domain.useCase.ExportUseCase


@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory
constructor(
    private val exportUseCase: ExportUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(exportUseCase) as T
    }
}


class SettingsViewModel constructor(
    private val exportUseCase: ExportUseCase
) : BaseViewModel() {

    private fun exportWords() {

        useCase(ExportUseCase.Param())
            .onExecute(exportUseCase::execute)
            .onFinally {

            }
            .onSuccess {

            }
            .onFailure {

            }.run()
    }

}