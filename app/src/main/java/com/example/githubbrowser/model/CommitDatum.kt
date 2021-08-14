package com.example.githubbrowser.model

data class CommitDatum(
    val sha: String,

    val commit: Commit,

    val author: Author__1?=null
)