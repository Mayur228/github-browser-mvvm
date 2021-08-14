package com.example.githubbrowser.model

import com.squareup.moshi.Json


data class GithubModel(
    val name: String,

    val owner: Owner,

    @Json(name = "html_url")
    val htmlUrl: String,

    val description: String,

    val url: String,
    var open_issues_count: Int
)