package com.example.githubbrowser.ui.commitactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.model.CommitDatum
import de.hdodenhof.circleimageview.CircleImageView

class CommitAdapter(list: List<CommitDatum>,val context: Context) : RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    var list:List<CommitDatum> = list
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.commit_items,parent,false)
        return CommitViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
       val model:CommitDatum= list[position]
       model.let { holder.bind(model) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

  inner  class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val date: TextView=itemView.findViewById(R.id.txt_date)
        private val sha: TextView=itemView.findViewById(R.id.txt_id)
        private val username: TextView=itemView.findViewById(R.id.user_name)
        private val mess: TextView=itemView.findViewById(R.id.mess)
        private val profileImg: CircleImageView=itemView.findViewById(R.id.profile_image)

        fun bind(model: CommitDatum){

            date.text=model.commit.author.date.substring(0,model.commit.author.date.lastIndexOf("T"))
            sha.text=model.sha
            username.text=model.commit.author.name
            mess.text=model.commit.message

            Glide.with(context)
                .load(model.author?.avatar_Url)
                .error(R.drawable.profile)
                .into(profileImg)

        }

        }

}