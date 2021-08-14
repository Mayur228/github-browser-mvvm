package com.example.githubbrowser.ui.detailactivity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.githubbrowser.R
import com.example.githubbrowser.ui.branchfragment.BranchFragment
import com.example.githubbrowser.ui.issuefragment.IssueFragment
import com.google.android.material.tabs.TabLayout

class DetailsActivity : AppCompatActivity() {

    private val repoName: TextView by lazy {
        findViewById(R.id.repo_name)
    }

    private val repoDes: TextView by lazy {
        findViewById(R.id.repo_des)
    }

    private val tabs: TabLayout by lazy {
        findViewById(R.id.tab_layout)
    }

    private lateinit var browserUrl: String

    lateinit var  bundle:Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_browser ->{
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(browserUrl)
                startActivity(intent)
                true
            }

            android.R.id.home->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setup() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        supportActionBar?.title="Details"


        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        repoName.text = intent.getStringExtra("RepoName")
        repoDes.text = intent.getStringExtra("RepoDes")
        browserUrl = intent.getStringExtra("BrowserUrl").toString()

        tabs.getTabAt(1)?.text="Issues("+intent.getIntExtra("IssueCount",0)+")"

        bundle = Bundle().apply {
            putString("OwnerName", intent.getStringExtra("OwnerName"))
            putString("RepoName", intent.getStringExtra("RepoName"))
        }

        changeFragment(BranchFragment().newInstance(), "Branch")
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> changeFragment(BranchFragment().newInstance(), "Branch")
                    1 -> changeFragment(IssueFragment().newInstance(), "Issue")
                    else -> Log.e("BREAK", "BREAK")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    fun changeFragment(fragment: Fragment, fragmentTag: String?) {
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
            fragmentTransaction.replace(R.id.frame, fragment, fragmentTag)
            fragment.arguments=bundle
            fragmentTransaction.commit()
        } catch (e: java.lang.Exception) {
            Log.d("catch-Frag_Transaction", e.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}