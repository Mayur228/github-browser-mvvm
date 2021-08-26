package com.example.githubbrowser.ui.addrepoactivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import com.example.githubbrowser.R

class AddRepoActivity : AppCompatActivity() {

    private val etxRepo:EditText by lazy {
        findViewById(R.id.ext_repo)
    }

    private val etxOwner:EditText by lazy {
        findViewById(R.id.ext_owner)
    }

    private val add: Button by lazy {
        findViewById(R.id.add_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)

        setup()
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

    private fun setup() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        title="Add Repo"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))


        add.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent()
                    .putExtra("REPOOWNER", etxOwner.text.toString().trim())
                    .putExtra("REPONAME", etxRepo.text.toString().trim())
            )

            finish()
        }

    }


}