package com.example.githubbrowser.ui.githubactivity.di
//
import android.content.Context
import com.example.githubbrowser.ui.detailactivity.DetailsActivity
import com.example.githubbrowser.ui.githubactivity.GithubActivity
import com.example.githubbrowser.ui.githubactivity.GithubAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class GithubAdapterModule {

    @Provides
    @ActivityScoped
    fun provideGithubAdapter(@ActivityContext activity: Context): GithubAdapter{
        return GithubAdapter(
            (activity as GithubActivity).onRepositoryClicked,(activity as GithubActivity).onRepositoryShared)
    }
}