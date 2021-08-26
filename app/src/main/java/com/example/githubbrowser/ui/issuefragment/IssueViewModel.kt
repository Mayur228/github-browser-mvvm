package com.example.githubbrowser.ui.issuefragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.IssueDatum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(private val githubRepository: Githubrepository):ViewModel() {
    val data = MutableLiveData<List<IssueDatum>>()
    val errorMessage = MutableLiveData<String>()
    val model:IssueDatum?=null

    fun getIssue(owner:String, repoName:String, page:Int){
        githubRepository.getIssueData(owner, repoName, page)
            .subscribe({
                       data.value=it
            },{
                errorMessage.value=it.message
            })
    }

}