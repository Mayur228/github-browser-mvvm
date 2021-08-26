package com.example.githubbrowser.ui.githubactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.Event
import com.example.githubbrowser.data.SingleLiveEvent
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(private val githubRepository: Githubrepository) :
    ViewModel() {

    val repoData = MutableLiveData<List<GithubBrowserEntity>>()

    private val errorMessage = MutableLiveData<String>()

    val viewRepositoryDetailsEvent = MutableLiveData<Event<GithubBrowserEntity>>()

    val addNewRepositoryEvent = SingleLiveEvent<Unit>()

    private val _shareRepositoryEvent = MutableLiveData<Event<GithubBrowserEntity>>()
    val shareRepositoryEvent: LiveData<Event<GithubBrowserEntity>>
        get() = _shareRepositoryEvent

    init {
        displayRepository()
    }

    fun getRepo(ownerName: String, repoName: String) {
        githubRepository.getRepoData(ownerName, repoName)
            .subscribe({},
                {
                    errorMessage.value = it.message
                })
    }

    fun viewRepositoryDetails(repository: GithubBrowserEntity) {
        viewRepositoryDetailsEvent.value = Event(repository)
    }

    fun addNewRepository() {
        addNewRepositoryEvent.value = Unit
    }

    fun shareRepository(model: GithubBrowserEntity) {
        _shareRepositoryEvent.value = Event(model)
    }

    fun displayRepository() {
        githubRepository.getRepository().subscribe(
            {
                repoData.value = it
            },
            {
                errorMessage.value = it.message
            }
        )
    }

    fun deleteRepository(pos: Int) {

        val existingData = repoData.value ?: return

        githubRepository.deleteRepository(existingData[pos])
            .subscribe(
                {},
                {}
            )
    }

}