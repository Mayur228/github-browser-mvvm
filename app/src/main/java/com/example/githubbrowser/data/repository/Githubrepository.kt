package com.example.githubbrowser.data.repository

import com.example.githubbrowser.data.api.RetrofitServices
import com.example.githubbrowser.model.BranchDatum
import com.example.githubbrowser.model.CommitDatum
import com.example.githubbrowser.model.GithubModel
import com.example.githubbrowser.model.IssueDatum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class Githubrepository(private val retrofitServices: RetrofitServices) {

    fun getRepoData(
        username:String,
        repoName:String,
        success: (data: GithubModel) -> Unit,
        failure: (error: Throwable) -> Unit
    ){
        retrofitServices.getRepo(username, repoName).enqueue(object :Callback<GithubModel>{
            override fun onResponse(
                call: Call<GithubModel>,
                response: Response<GithubModel>
            ) {
                response
                    .body()
                    ?.let { success(it) }
                    ?: failure(Exception("No data"))
            }

            override fun onFailure(call: Call<GithubModel>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getBranchData(owner:String, repoName:String, success: (data: List<BranchDatum>) -> Unit, failure: (error: Throwable) -> Unit){
        retrofitServices.getBranch(owner,repoName).enqueue(object: Callback<List<BranchDatum>>{
            override fun onResponse(call: Call<List<BranchDatum>>, response: Response<List<BranchDatum>>) {
                response.body()?.let {
                    success(it)
                }?: failure(Exception("No Data"))

            }

            override fun onFailure(call: Call<List<BranchDatum>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getIssueData(owner:String,reponame:String,page:Int,success: (data: List<IssueDatum>) -> Unit,failure: (error: Throwable) -> Unit){
        retrofitServices.getIssue(owner,reponame,page).enqueue(object: Callback<List<IssueDatum>>{
            override fun onResponse(call: Call<List<IssueDatum>>, response: Response<List<IssueDatum>>) {
                response.body()?.let {
                    success(it)
                }?: failure(Exception("No Data"))

            }

            override fun onFailure(call: Call<List<IssueDatum>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getCommitData(owner: String,reponame: String,sha: String,success: (data: List<CommitDatum>) -> Unit,failure: (error: Throwable) -> Unit){
        retrofitServices.getCommit(owner,reponame,sha).enqueue(object :Callback<List<CommitDatum>>{
            override fun onResponse(
                call: Call<List<CommitDatum>>,
                response: Response<List<CommitDatum>>
            ) {
                response.body()?.let {
                    success(it)
                }?: failure(Exception("NO DATA"))
            }

            override fun onFailure(call: Call<List<CommitDatum>>, t: Throwable) {
                failure(t)
            }

        })
    }
}
