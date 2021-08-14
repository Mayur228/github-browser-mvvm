package com.example.githubbrowser.ui.branchfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.model.BranchDatum

class BranchAdapter(var list: List<BranchDatum>,val onCommitClick:(branchData:BranchDatum)->Unit):RecyclerView.Adapter<BranchAdapter.BranchViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.branch_item,parent,false)
        return BranchViewHolder(view)
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(list[position],onCommitClick)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    class BranchViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private val branchName: TextView=itemView.findViewById(R.id.branch_name)

        fun bind(model:BranchDatum,onCommitClick:(branchData:BranchDatum)->Unit){
            branchName.text=model.name

            itemView.setOnClickListener {
                onCommitClick(model)
            }
        }

    }


}