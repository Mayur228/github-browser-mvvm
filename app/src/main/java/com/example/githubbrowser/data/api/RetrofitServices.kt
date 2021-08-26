package com.example.githubbrowser.data.api

import com.example.githubbrowser.model.BranchDatum
import com.example.githubbrowser.model.CommitDatum
import com.example.githubbrowser.model.GithubModel
import com.example.githubbrowser.model.IssueDatum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

interface RetrofitServices {

    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") username: String,
        @Path("repo") repoName: String
    ): Single<GithubModel>

    @GET("repos/{owner}/{repo}/branches")
    fun getBranch(
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Single<List<BranchDatum>>

    @GET("repos/{owner}/{repo}/issues")
    fun getIssue(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Query("page") page: Int
    ): Single<List<IssueDatum>>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommit(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Query("sha") sha: String
    ): Single<List<CommitDatum>>

}