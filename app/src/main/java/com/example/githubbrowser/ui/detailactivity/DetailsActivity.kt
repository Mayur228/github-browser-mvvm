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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.githubbrowser.R
import com.example.githubbrowser.database.entity.GithubBrowserEntity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity(), RepositoryDeletionDialogFragment.Callback {

    companion object {

        private val TAG = DetailsActivity::class.java.simpleName

        private val EXTRA_REPOSITORY = "$TAG.EXTRA_REPOSITORY"

        fun getStartIntent(
            context: Context,
            model: GithubBrowserEntity
        ): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_REPOSITORY, model)
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

    private val repository: GithubBrowserEntity
        get() = intent.getParcelableExtra<GithubBrowserEntity>(EXTRA_REPOSITORY)
            ?: throw IllegalArgumentException("Unable to find extra $EXTRA_REPOSITORY")

    private val detailsViewModel: DetailsViewModel by viewModels()

    @Inject
    lateinit var tabAdapter: DetailTabAdapter

    private lateinit var browserUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setup()

        observeOpenBrowser()

        observeDeletionDialog()

        observeDeletionSuccessEvent()

    }

    val repositoryName
        get() = repository.repoName

    val ownerName
        get() = repository.ownerName

    private fun setup() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        supportActionBar?.title = getString(R.string.details)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        repoName.text = repositoryName
        repoDes.text = repository.repoDes
        browserUrl = repository.repoUrl

        detailVP.adapter = tabAdapter

        TabLayoutMediator(
            tabs, detailVP
        ) { tab, position ->

            if (position == 0) {
                tab.text = "Branch"

            } else {
                tab.text = "Issue(" + repository.issueCount + ")"
            }
        }.attach()

    }

    private fun observeOpenBrowser() {
        detailsViewModel._openBrowserEvent.observe(this, {
            it.getContentIfNotHandled()?.let { uri ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            }
        })
    }

    private fun observeDeletionDialog() {
        detailsViewModel.openDeletionDialogEvent.observe(this, {
            it.getContentIfNotHandled()?.let {
                RepositoryDeletionDialogFragment.newInstance(it)
                    .show(supportFragmentManager, "DeleteDialog")
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
                detailsViewModel.openDeletionDialog(repository.repoName)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeDeletionSuccessEvent() {
        detailsViewModel.repositoryDeleteEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                finish()
            }
        })
    }

    override fun onRepositoryDelete(isDeleted: Boolean) {
        if (isDeleted) {

            setResult(Activity.RESULT_OK, Intent().putExtra("DeleteRepo", true))
            finish()

            detailsViewModel.deleteRepository(repository)
        }
    }
}

class RepositoryDeletionDialogFragment : DialogFragment() {

    companion object {
        private val TAG = RepositoryDeletionDialogFragment::class.java.simpleName

        private val EXTRA_REPO_NAME = "$TAG.EXTRA_OWNER_NAME"
        fun newInstance(repoName: String): RepositoryDeletionDialogFragment {
            val args = Bundle()
            args.putString(EXTRA_REPO_NAME, repoName)
            val fragment = RepositoryDeletionDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var callback: Callback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    "Yes"
                ) { dialog, id ->
                    callback?.onRepositoryDelete(true)

                }
                setNegativeButton(
                    "No"
                ) { dialog, id ->
                    callback?.onRepositoryDelete(false)
                }

                setTitle("Are you sure you want to delete ${arguments?.getString("repoName")}?")
            }
            builder.create()
        } ?: throw (IllegalStateException("Activity Not Found"))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    interface Callback {
        fun onRepositoryDelete(isDeleted: Boolean)
    }
}
