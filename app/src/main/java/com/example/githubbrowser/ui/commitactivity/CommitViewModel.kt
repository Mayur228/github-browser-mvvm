package com.example.githubbrowser.ui.commitactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.CommitDatum

class CommitViewModel(private val githubRepository: Githubrepository): ViewModel() {
    val data= MutableLiveData<List<CommitDatum>>()
    val errorMessage= MutableLiveData<String>()

    fun getCommit(owner:String, repoName:String, sha:String){
        githubRepository.getCommitData(owner,repoName,sha,{
            data.value=it
        },{
            errorMessage.value=it.message
        })
    }
}