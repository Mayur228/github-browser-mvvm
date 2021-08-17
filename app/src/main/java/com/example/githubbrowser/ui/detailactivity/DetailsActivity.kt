package com.example.githubbrowser.ui.detailactivity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.githubbrowser.R
import com.example.githubbrowser.model.GithubModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    companion object {

        private val TAG = DetailsActivity::class.java.simpleName

        private val EXTRA_OWNER_NAME = "$TAG.EXTRA_OWNER_NAME"

        fun getStartIntent(
            context: Context,
            model: GithubModel
        ): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_OWNER_NAME, model.owner.login)
            intent.putExtra("RepoName", model.name)
            intent.putExtra("RepoDes", model.description)
            intent.putExtra("BrowserUrl", model.htmlUrl)
            intent.putExtra("IssueCount", model.open_issues_count)
            return intent

        }


    }

    private val repoName: TextView by lazy {
        findViewById(R.id.repo_name)
    }

    private val repoDes: TextView by lazy {
        findViewById(R.id.repo_des)
    }

    private val tabs: TabLayout by lazy {
        findViewById(R.id.tab_layout)
    }

    private val detailVP: ViewPager2 by lazy {
        findViewById(R.id.detailVP)
    }

    private lateinit var browserUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setup()
    }

    private val repositoryName: String
        get() = intent.getStringExtra("RepoName") ?: ""

    private val ownerName: String
    get() = intent.getStringExtra(EXTRA_OWNER_NAME) ?: ""



    private fun setup() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        supportActionBar?.title = "Details"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        repoName.text = repositoryName
        repoDes.text = intent.getStringExtra("RepoDes")
        browserUrl = intent.getStringExtra("BrowserUrl").toString()

        detailVP.adapter = DetailTabAdapter(
            ownerName,
            repositoryName,
            this@DetailsActivity
        )

        TabLayoutMediator(
            tabs, detailVP
        ) { tab, position ->

            if (position == 0) {
                tab.text = "Branch"

            } else {
                tab.text = "Issue(" + intent.getIntExtra("IssueCount", 0) + ")"
            }
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_browser -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(browserUrl)
                startActivity(intent)
                true
            }

            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}