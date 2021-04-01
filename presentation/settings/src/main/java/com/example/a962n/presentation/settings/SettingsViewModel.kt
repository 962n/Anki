package com.example.a962n.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a962n.anki.component.presentation.BaseViewModel


@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory
constructor(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel() as T
    }
}


class SettingsViewModel : BaseViewModel() {

}