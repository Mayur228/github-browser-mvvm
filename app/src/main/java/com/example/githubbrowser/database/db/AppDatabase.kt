package com.example.githubbrowser.database.db

import androidx.room.*
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import com.example.githubbrowser.database.dao.GithubBrowserDao

@Database(
    entities = [GithubBrowserEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val githubBrowserDoa:GithubBrowserDao
}