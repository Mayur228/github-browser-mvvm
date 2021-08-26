package com.example.githubbrowser.ui.branchfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubbrowser.data.SingleLiveEvent
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.BranchDatum
import com.example.githubbrowser.model.CommitLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor (private val githubRepository: Githubrepository):ViewModel() {
    val data = MutableLiveData<List<BranchDatum>>()
    private val errorMessage = MutableLiveData<String>()

    var openCommitEvent=SingleLiveEvent<CommitLiveData>()

    fun getBranch(ownerName:String, repoName:String){
       githubRepository.getBranchData(ownerName,repoName)
           .subscribe({
                      data.value=it
           },{
               errorMessage.value=it.message
           })
    }

    fun openCommit(commitLiveData: CommitLiveData){
        openCommitEvent.value=commitLiveData
    }

}