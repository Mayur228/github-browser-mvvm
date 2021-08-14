package com.example.githubbrowser.ui.githubactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.Event
import com.example.githubbrowser.data.SingleLiveEvent
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.GithubModel

class GithubViewModel(private val githubRepository: Githubrepository) : ViewModel() {

    val data = MutableLiveData<List<GithubModel>>()
    val errorMessage = MutableLiveData<String>()


    val viewRepositoryDetailsEvent=MutableLiveData<Event<GithubModel>>()
//    val viewRepositoryDetailsEvent=SingleLiveEvent<GithubModel>()

    val addNewRepositoryEvent = SingleLiveEvent<Unit>()

//    val shareRepositoryEvent = SingleLiveEvent<GithubModel>()


    fun getRepo(ownerName: String, repoName: String) {
        githubRepository
            .getRepoData(
                ownerName,
                repoName,
                {
                    val existingList = (data.value ?: listOf()).toMutableList()
                    existingList.add(it)
                    data.value = existingList
                },
                {
                    errorMessage.value = it.message
                }
            )

    }

    fun viewRepositoryDetails(repository: GithubModel) {
        viewRepositoryDetailsEvent.value=Event(repository)
    }

    fun addNewRepository() {
        addNewRepositoryEvent.value = Unit
    }

//    fun shareRepositoryEvent(repository: GithubModel) {
//        shareRepositoryEvent.value = repository
//    }
}