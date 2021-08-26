package com.example.githubbrowser.ui.detailactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.Event
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val githubRepository: Githubrepository
) : ViewModel() {

    val _openBrowserEvent = MutableLiveData<Event<String>>()

//    val openBrowserEvent:LiveData<Event<String>>
//     get() = _openBrowserEvent

    private val _openDeletionDialogEvent = MutableLiveData<Event<String>>()
    val openDeletionDialogEvent: LiveData<Event<String>>
        get() = _openDeletionDialogEvent

    private val _repositoryDeleteEvent = MutableLiveData<Event<Unit>>()
    val repositoryDeleteEvent: LiveData<Event<Unit>>
        get() = _repositoryDeleteEvent

    fun openBrowser(repoUrl: String) {
        _openBrowserEvent.value = Event(repoUrl)
    }

    fun openDeletionDialog(repoName: String) {
        _openDeletionDialogEvent.value = Event(repoName)
    }

    fun deleteRepository(repository: GithubBrowserEntity) {
        githubRepository
            .deleteRepository(repository)
            .subscribe(
                {
                    _repositoryDeleteEvent.value = Event(Unit)
                },
                {}
            )
    }
}