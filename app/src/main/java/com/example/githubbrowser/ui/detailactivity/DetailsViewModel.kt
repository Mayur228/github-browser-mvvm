package com.example.githubbrowser.ui.detailactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.Event

class DetailsViewModel : ViewModel() {

     val _openBrowserEvent = MutableLiveData<Event<String>>()

//    val openBrowserEvent:LiveData<Event<String>>
//     get() = _openBrowserEvent

    private val _openDeletionDialogEvent = MutableLiveData<Event<String>>()

    val openDeletionDialogEvent : LiveData<Event<String>>
        get() = _openDeletionDialogEvent

    fun openBrowser(repoUrl:String){
        _openBrowserEvent.value = Event(repoUrl)
    }

    fun openDeletionDialog(repoName:String){
        _openDeletionDialogEvent.value= Event(repoName)
    }
}