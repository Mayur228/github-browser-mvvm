package com.example.githubbrowser.ui.issuefragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.model.IssueDatum
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class IssueAdapter @Inject constructor(list: List<IssueDatum>, var contaxt: Context) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    var list: List<IssueDatum> = list
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issue_item, parent, false)
        return IssueViewHolder(view)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val issueTitle: TextView = itemView.findViewById(R.id.issue_title)
        private val creatorName: TextView = itemView.findViewById(R.id.creator_name)
        private val profileImg: CircleImageView = itemView.findViewById(R.id.profile_image)

        fun bind(model: IssueDatum) {
            issueTitle.text = model.title
            creatorName.text = model.user.login
            Glide.with(contaxt)
                .load(model.user.avatarUrl)
                .error(R.drawable.profile)
                .into(profileImg)

        }
    }

}