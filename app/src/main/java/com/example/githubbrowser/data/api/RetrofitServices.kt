package com.example.githubbrowser.data.api

import com.example.githubbrowser.model.BranchDatum
import com.example.githubbrowser.model.CommitDatum
import com.example.githubbrowser.model.GithubModel
import com.example.githubbrowser.model.IssueDatum
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://api.github.com/"

interface RetrofitServices {

    @GET("repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") username: String,
        @Path("repo") reponame: String
    ): Call<GithubModel>

    @GET("repos/{owner}/{repo}/branches")
    fun getBranch(
        @Path("owner") owner: String,
        @Path("repo") reponame: String
    ): Call<List<BranchDatum>>

    @GET("repos/{owner}/{repo}/issues")
    fun getIssue(
        @Path("owner") owner: String,
        @Path("repo") reponame: String,
        @Query("page")page:Int
    ): Call<List<IssueDatum>>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommit(
        @Path("owner") owner: String,
        @Path("repo") reponame: String,
        @Query("sha") sha: String
    ): Call<List<CommitDatum>>

    companion object {

        var retrofitServices: RetrofitServices? = null

        fun getInstance(): RetrofitServices {

            if (retrofitServices == null) {

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                val retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                retrofitServices = retrofit.create(RetrofitServices::class.java)

            }

            return retrofitServices!!

        }
    }
}