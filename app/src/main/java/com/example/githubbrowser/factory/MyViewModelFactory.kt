package com.example.githubbrowser.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubbrowser.data.api.RetrofitServices
import com.example.githubbrowser.data.repository.Githubrepository
import com.example.githubbrowser.ui.branchfragment.BranchViewModel
import com.example.githubbrowser.ui.commitactivity.CommitViewModel
import com.example.githubbrowser.ui.detailactivity.DetailsViewModel
import com.example.githubbrowser.ui.githubactivity.GithubViewModel
import com.example.githubbrowser.ui.issuefragment.IssueViewModel
import java.lang.IllegalArgumentException

object MyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(GithubViewModel::class.java) -> {
                GithubViewModel(Githubrepository(RetrofitServices.getInstance()))as T
            }
            modelClass.isAssignableFrom(BranchViewModel::class.java) -> {
                BranchViewModel(Githubrepository(RetrofitServices.getInstance()))as T
            }
            modelClass.isAssignableFrom(IssueViewModel::class.java) -> {
                IssueViewModel(Githubrepository(RetrofitServices.getInstance()))as T
            }
            modelClass.isAssignableFrom(CommitViewModel::class.java) -> {
                CommitViewModel(Githubrepository(RetrofitServices.getInstance()))as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel() as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}