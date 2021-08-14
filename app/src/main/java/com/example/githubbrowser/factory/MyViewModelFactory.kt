package com.example.githubbrowser.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubbrowser.data.api.RetrofitServices
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.model.GithubModel
import com.example.githubbrowser.model.IssueDatum
import com.example.githubbrowser.ui.branchfragment.BranchViewModel
import com.example.githubbrowser.ui.commitactivity.CommitViewModel
import com.example.githubbrowser.ui.githubactivity.GithubViewModel
import com.example.githubbrowser.ui.issuefragment.IssueViewModel
import java.lang.IllegalArgumentException

object MyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GithubViewModel::class.java)){
            return GithubViewModel(Githubrepository(RetrofitServices.getInstance()))as T
        }else if (modelClass.isAssignableFrom(BranchViewModel::class.java)){
            return BranchViewModel(Githubrepository(RetrofitServices.getInstance()))as T
        }else if (modelClass.isAssignableFrom(IssueViewModel::class.java)){
            return IssueViewModel(Githubrepository(RetrofitServices.getInstance()))as T
        }else if (modelClass.isAssignableFrom(CommitViewModel::class.java)){
            return CommitViewModel(Githubrepository(RetrofitServices.getInstance()))as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
//        return if (modelClass.isAssignableFrom(GithubViewModel::class.java)){
//            GithubViewModel(Githubrepository(RetrofitServices.getInstance()))as T
//        }else{
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
    }
}