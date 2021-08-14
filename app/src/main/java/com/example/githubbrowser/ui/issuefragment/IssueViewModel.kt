package com.example.githubbrowser.ui.issuefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.IssueDatum

class IssueViewModel(val githubRepository: Githubrepository):ViewModel() {
    val data = MutableLiveData<List<IssueDatum>>()
    val errorMessage = MutableLiveData<String>()

    fun getIssue(owner:String, repoName:String, page:Int){
        githubRepository.getIssueData(owner,repoName,page,{
            data.value= it
        },
            {
                errorMessage.value=it.message
            })
    }

}