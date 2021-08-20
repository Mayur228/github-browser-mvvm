package com.example.githubbrowser.ui.githubactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.Event
import com.example.githubbrowser.data.SingleLiveEvent
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.GithubModel

class GithubViewModel(private val githubRepository: Githubrepository) : ViewModel() {

    val data = MutableLiveData<List<GithubModel>>()
    val errorMessage = MutableLiveData<String>()

    val viewRepositoryDetailsEvent = MutableLiveData<Event<GithubModel>>()

    val addNewRepositoryEvent = SingleLiveEvent<Unit>()

    private val _shareRepositoryEvent = MutableLiveData<Event<GithubModel>>()
    val shareRepositoryEvent: LiveData<Event<GithubModel>>
        get() = _shareRepositoryEvent

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
        viewRepositoryDetailsEvent.value = Event(repository)
    }

    fun addNewRepository() {
        addNewRepositoryEvent.value = Unit
    }

    fun shareRepository(model: GithubModel) {
        _shareRepositoryEvent.value = Event(model)
    }

    fun deleteRepository(repositoryName: String,pos: Int) {
        val existingData = data.value?.toMutableList()

//        var indexToDelete = -1
//
//        for (i in 0 until (existingData?.size ?: 0)) {
//            if(existingData?.get(i)?.name == repositoryName) {
//                indexToDelete = i;
//                break
//            }
//        }
//
//        if(indexToDelete >= 0) {
//            existingData?.removeAt(indexToDelete)
//        }

        if (repositoryName.isEmpty()){
            existingData?.removeAt(pos)
            data.value = existingData

        }else{
            existingData?.removeAll {
                it.name == repositoryName
            }
            data.value = existingData
        }



    }

}