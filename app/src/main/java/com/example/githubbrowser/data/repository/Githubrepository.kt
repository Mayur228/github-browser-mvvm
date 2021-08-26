package com.example.githubbrowser.data.repository

import com.example.githubbrowser.data.api.RetrofitServices
import com.example.githubbrowser.database.db.AppDatabase
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import com.example.githubbrowser.model.BranchDatum
import com.example.githubbrowser.model.CommitDatum
import com.example.githubbrowser.model.GithubModel
import com.example.githubbrowser.model.IssueDatum
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Githubrepository @Inject constructor(
    private val retrofitServices: RetrofitServices,
    private val database: AppDatabase
) {

    fun getRepoData(
        username: String,
        repoName: String,
    ): Single<GithubModel> {
        // check if already in database

        return retrofitServices
            .getRepo(username, repoName)
            .flatMap { githubModel ->
                database
                    .githubBrowserDoa.addRepository(
                        GithubBrowserEntity(
                            id = 0,
                            repoName = githubModel.name,
                            repoDes = githubModel.description,
                            repoUrl = githubModel.htmlUrl,
                            ownerName = githubModel.owner.login,
                            issueCount = githubModel.open_issues_count
                        )
                    )
                    .map {  githubModel }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBranchData(owner: String, repoName: String): Single<List<BranchDatum>> {
        return retrofitServices.getBranch(owner, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getIssueData(owner: String, repoName: String, page: Int): Single<List<IssueDatum>> {
        return retrofitServices.getIssue(owner, repoName, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCommitData(owner: String, repoName: String, sha: String): Single<List<CommitDatum>> {
        return retrofitServices.getCommit(owner, repoName, sha)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRepository(): Observable<List<GithubBrowserEntity>> {

        return database
            .githubBrowserDoa
            .getRepository()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteRepository(model: GithubBrowserEntity) : Completable{
        return database
            .githubBrowserDoa
            .deleteRepository(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
