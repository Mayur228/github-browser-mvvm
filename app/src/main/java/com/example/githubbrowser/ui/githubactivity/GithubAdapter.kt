package com.example.githubbrowser.ui.githubactivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.model.GithubModel

class GithubAdapter(
    list: List<GithubModel>,
    private val onRepositoryClicked: (repository: GithubModel) -> Unit,
    private val onRepositoryShared: (repository: GithubModel) -> Unit
) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {

    var list: List<GithubModel> = list
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.github_item, parent, false)
        return GithubViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(
            list[position],
            onRepositoryClicked,
            onRepositoryShared
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val repoName: TextView = itemView.findViewById(R.id.repo_name)
        private val repoDes: TextView = itemView.findViewById(R.id.repo_des)
        private val share: ImageView = itemView.findViewById(R.id.send_icon)

        fun bind(
            model: GithubModel,
            onRepositoryClicked: (repository: GithubModel) -> Unit,
            onRepositoryShared: (repository: GithubModel) -> Unit
        ) {
            repoName.text = model.name
            repoDes.text = model.description

            itemView.setOnClickListener {
                onRepositoryClicked(model)
            }

            share.setOnClickListener {
                onRepositoryShared(model)
            }
        }

    }
}