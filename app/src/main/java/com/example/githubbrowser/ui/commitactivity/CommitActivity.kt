package com.example.githubbrowser.ui.commitactivity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.factory.MyViewModelFactory

class CommitActivity : AppCompatActivity() {
    private val commitRV:RecyclerView by lazy {
        findViewById(R.id.commit_view)
    }

    private val commitViewModel by lazy {
        ViewModelProvider(this,MyViewModelFactory).get(CommitViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commit)

        setUp()
        observeCommitData()

    }

    private fun observeCommitData() {
        commitViewModel.data.observe(this,{
            commitRV.adapter=CommitAdapter(it,applicationContext)
            (commitRV.adapter as CommitAdapter).list=it
        })
    }

    private fun setUp() {

        val owner=intent.getStringExtra("OwnerName").toString()
        val repo=intent.getStringExtra("RepoName").toString()
        val sha=intent.getStringExtra("SHA").toString()

        commitViewModel.getCommit(owner,repo,sha)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        supportActionBar?.title="Commits"
        supportActionBar?.subtitle=sha
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        commitRV.layoutManager=LinearLayoutManager(applicationContext,RecyclerView.VERTICAL,false)

        commitRV.adapter=CommitAdapter(listOf(),applicationContext)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}