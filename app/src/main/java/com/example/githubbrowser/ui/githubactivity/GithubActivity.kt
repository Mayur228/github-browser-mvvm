package com.example.githubbrowser.ui.githubactivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.factory.MyViewModelFactory
import com.example.githubbrowser.ui.addrepoactivity.AddRepoActivity
import com.example.githubbrowser.ui.detailactivity.DetailsActivity
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView


class GithubActivity : AppCompatActivity() {

    private val txt: TextView by lazy {
        findViewById(R.id.txt)
    }

    private val addBtn: Button by lazy {
        findViewById(R.id.add_btn)
    }

    private val listRV: SwipeableRecyclerView by lazy {
        findViewById(R.id.rv)
    }

    private val githubViewModel: GithubViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory).get(GithubViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landing_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                githubViewModel.addNewRepository()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUp()

        observeGithubData()

        observeRepositoryDetailEvent()

        observeAddNewRepositoryEvent()

        observeShareRepositoryEvent()
    }

    private fun setUp() {

        addBtn.setOnClickListener {
            githubViewModel.addNewRepository()
        }

        listRV.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        listRV.adapter = GithubAdapter(
            listOf(),
            onRepositoryClicked = { data ->
                githubViewModel.viewRepositoryDetails(data)
            },
            onRepositoryShared = { model ->
                githubViewModel.shareRepository(model)
            }
        )
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

//        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
//            ItemTouchHelper.SimpleCallback(
//                0,
//                ItemTouchHelper.LEFT
//            ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                Toast.makeText(this@GithubActivity, "on Move", Toast.LENGTH_SHORT).show()
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                Toast.makeText(this@GithubActivity, "on Swiped $swipeDir", Toast.LENGTH_SHORT)
//                    .show()
//                githubViewModel.deleteRepository("", viewHolder.adapterPosition)
//            }
//
//        }
//
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(listRV)

        listRV.setListener(object : SwipeLeftRightCallback.Listener{
            override fun onSwipedLeft(position: Int) {
                githubViewModel.deleteRepository("", position)

            }

            override fun onSwipedRight(position: Int) {
                githubViewModel.deleteRepository("", position)

            }

        })

    }

    private fun observeGithubData() {

        githubViewModel.data.observe(this, {

            if (it == null) {
                txt.visibility = View.VISIBLE
                addBtn.visibility = View.VISIBLE
                listRV.visibility = View.GONE
            } else {
                txt.visibility = View.GONE
                addBtn.visibility = View.GONE
                listRV.visibility = View.VISIBLE

                (listRV.adapter as? GithubAdapter)
                    ?.list = it

            }

        })
    }

    private fun observeRepositoryDetailEvent() {
        githubViewModel.viewRepositoryDetailsEvent.observe(this, { event ->
            event.getContentIfNotHandled()?.let {
                val intent = DetailsActivity.getStartIntent(this@GithubActivity, it)
                deleteResultLauncher.launch(intent)
            }

        })
    }

    private fun observeAddNewRepositoryEvent() {
        githubViewModel.addNewRepositoryEvent.observe(this, {
            val intent = Intent(this, AddRepoActivity::class.java)
            resultLauncher.launch(intent)
        })
    }

    private fun observeShareRepositoryEvent() {
        githubViewModel.shareRepositoryEvent.observe(this, {

            it.getContentIfNotHandled()?.let { model ->
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, model.htmlUrl)
                shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(Intent.createChooser(shareIntent, "Send to"))
            }

        })
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {

                val ownerName = it.data?.getStringExtra("REPOOWNER")
                val repoName = it.data?.getStringExtra("REPONAME")

                if (ownerName != null && repoName != null) {
                    githubViewModel.getRepo(ownerName, repoName)
                }

            }

        }

    private val deleteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val repoName = it.data?.getStringExtra("DeleteRepo")

                Toast.makeText(applicationContext, "$repoName Deleted", Toast.LENGTH_LONG).show()

                repoName
                    ?.let {
                        githubViewModel.deleteRepository(it, 0)
                    }
            }
        }
}