package com.example.githubbrowser.ui.detailactivity.di

import android.app.Activity
import android.content.Context
import com.example.githubbrowser.ui.detailactivity.DetailTabAdapter
import com.example.githubbrowser.ui.detailactivity.DetailsActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class DetailActivityModule {

    @Provides
    fun provideDetailTabAdapter(@ActivityContext activity: Context): DetailTabAdapter {

        val activity = activity as DetailsActivity

        return DetailTabAdapter(
            activity.ownerName,
            activity.repositoryName,
            activity
        )
    }

}