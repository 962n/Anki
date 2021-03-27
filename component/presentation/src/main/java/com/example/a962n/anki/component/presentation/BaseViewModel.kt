package com.example.a962n.anki.component.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.a962n.anki.component.presentation.ext.observe

abstract class BaseViewModel : ViewModel() {

    private var event: EventLiveData<ViewModelEvent> =
        EventLiveData(ViewModelEvent.None)

    fun event(owner: LifecycleOwner, observer: (ViewModelEvent) -> Unit) {
        owner.observe(event, observer)
    }

    protected fun handleEvent(event: ViewModelEvent) {
        this.event.setValue(event)
    }

    sealed class ViewModelEvent {
        object None : ViewModelEvent()
        abstract class FeatureViewModelEvent : ViewModelEvent()
    }
}