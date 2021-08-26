package com.example.githubbrowser.database.dao

import androidx.room.*
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface GithubBrowserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepository(repositoryEntity:GithubBrowserEntity) : Single<Long>

    @Query("SELECT * FROM GithubBrowserEntity")
    fun getRepository():Observable<List<GithubBrowserEntity>>

    @Delete
    fun deleteRepository(repositoryEntity:GithubBrowserEntity) : Completable


}