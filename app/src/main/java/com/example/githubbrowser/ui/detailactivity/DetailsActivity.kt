package com.example.githubbrowser.ui.detailactivity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.githubbrowser.R
import com.example.githubbrowser.factory.MyViewModelFactory
import com.example.githubbrowser.model.GithubModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity: AppCompatActivity(),RepositoryDeletionDialogFragment.Callback {

    companion object {

        private val TAG = DetailsActivity::class.java.simpleName

        private val EXTRA_OWNER_NAME = "$TAG.EXTRA_OWNER_NAME"

        private val EXTRA_REPO_NAME = "$TAG.EXTRA_REPO_NAME"

        private val EXTRA_REPO_DES = "$TAG.EXTRA_REPO_DES"

        private val EXTRA_BROWSER_URL = "$TAG.EXTRA_BROWSER_URL"

        private val EXTRA_ISSUE_COUNTER = "$TAG.EXTRA_ISSUE_COUNTER"

        fun getStartIntent(
            context: Context,
            model: GithubModel
        ):Intent{
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_OWNER_NAME, model.owner.login)
            intent.putExtra(EXTRA_REPO_NAME, model.name)
            intent.putExtra(EXTRA_REPO_DES, model.description)
            intent.putExtra(EXTRA_BROWSER_URL, model.htmlUrl)
            intent.putExtra(EXTRA_ISSUE_COUNTER, model.open_issues_count)
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

    private val detailsViewModel:DetailsViewModel by lazy {
        ViewModelProvider(this,MyViewModelFactory).get(DetailsViewModel::class.java)
    }

    private lateinit var browserUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setup()

        observeOpenBrowser()

        observeDeletionDialog()

    }

    private val repositoryName: String
        get() = intent.getStringExtra(EXTRA_REPO_NAME) ?: ""

    private val ownerName:String
        get() = intent.getStringExtra(EXTRA_OWNER_NAME) ?: ""

    private fun setup() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        supportActionBar?.title = "Details"

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        repoName.text = repositoryName
        repoDes.text = intent.getStringExtra(EXTRA_REPO_DES)
        browserUrl = intent.getStringExtra(EXTRA_BROWSER_URL).toString()

        detailVP.adapter = DetailTabAdapter(
            ownerName,
            repositoryName,
            this@DetailsActivity)

        TabLayoutMediator(
            tabs, detailVP
        ) { tab, position ->

            if (position == 0) {
                tab.text = "Branch"

            } else {
                tab.text = "Issue(" + intent.getIntExtra(EXTRA_ISSUE_COUNTER, 0) + ")"
            }
        }.attach()

    }

    private fun observeOpenBrowser() {
        detailsViewModel._openBrowserEvent.observe(this,{
            it.getContentIfNotHandled()?.let {uri->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            }
        })
    }

    private fun observeDeletionDialog(){
        detailsViewModel.openDeletionDialogEvent.observe(this,{
            it.getContentIfNotHandled()?.let {
                RepositoryDeletionDialogFragment.newInstance(it)
                    .show(supportFragmentManager,"DeleteDialog")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_browser -> {
                detailsViewModel.openBrowser(browserUrl)
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_delete -> {
                detailsViewModel.openDeletionDialog(repositoryName)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRepositoryDelete(isDeleted: Boolean) {
        if (isDeleted){
            setResult(Activity.RESULT_OK,Intent().putExtra("DeleteRepo",repositoryName))
            finish()

        }
    }

}

class RepositoryDeletionDialogFragment: DialogFragment() {

    companion object {
        private val TAG = RepositoryDeletionDialogFragment::class.java.simpleName

        private val EXTRA_REPO_NAME = "$TAG.EXTRA_OWNER_NAME"
        fun newInstance(repoName:String): RepositoryDeletionDialogFragment {
            val args = Bundle()
            args.putString(EXTRA_REPO_NAME,repoName)
            val fragment = RepositoryDeletionDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var callback: Callback?=null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Yes"
                ) { dialog, id ->
                    callback?.onRepositoryDelete(true)

                }
                setNegativeButton("No"
                ) { dialog, id ->
                    callback?.onRepositoryDelete(false)
                }

                setTitle("Are you sure you want to delete ${arguments?.getString("repoName")}?")
            }
            builder.create()
        }?:throw (IllegalStateException("Activity Not Found"))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback=context as?Callback
    }

    override fun onDetach() {
        super.onDetach()
        callback=null
    }

    interface Callback {
     fun onRepositoryDelete(isDeleted: Boolean)
    }
}
