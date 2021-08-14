package com.example.githubbrowser.model

data class BranchDatum(
    val name: String,

    val commit: Commit,

    val protected: Boolean
)
