package com.example.githubbrowser.ui.commitactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.CommitDatum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommitViewModel @Inject constructor (private val githubRepository: Githubrepository): ViewModel() {
    val data= MutableLiveData<List<CommitDatum>>()
    val errorMessage= MutableLiveData<String>()

    fun getCommit(owner:String, repoName:String, sha:String){
       githubRepository.getCommitData(owner, repoName, sha)
           .subscribe({
                      data.value=it
           },{
               errorMessage.value=it.message
           })
    }
}