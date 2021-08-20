package com.example.githubbrowser.ui.branchfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.factory.MyViewModelFactory
import com.example.githubbrowser.model.CommitLiveData
import com.example.githubbrowser.ui.commitactivity.CommitActivity
import com.example.githubbrowser.ui.issuefragment.IssueFragment

class BranchFragment : Fragment() {

    companion object {

        fun newInstance(owner: String, repositoryName: String): BranchFragment {
            val args = Bundle()
            args.putString("ownerName", owner)
            args.putString("repoName", repositoryName)
            val fragment = BranchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var branchNAme: TextView? = null
    private var branchRV: RecyclerView? = null

    private var branchViewModel: BranchViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.branchesfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp(view)
        observeBranchData()
        observeOpenCommit()
    }

    private fun setUp(view: View) {
        branchNAme = view.findViewById(R.id.branch)

        branchRV = view.findViewById(R.id.branch_view)

        branchRV?.layoutManager =
            LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        branchViewModel =
            ViewModelProvider(this, MyViewModelFactory).get(BranchViewModel::class.java)

        branchViewModel?.getBranch(
            arguments?.getString("ownerName").toString(),
            arguments?.getString("repoName").toString()
        )
    }

    private fun observeBranchData() {
        branchViewModel?.data?.observe(requireActivity(), {

            if (it.isEmpty()) {
                branchNAme?.visibility = View.VISIBLE
                branchNAme?.text = ("No Branches")
            } else {
                branchNAme?.visibility = View.GONE

                branchRV?.adapter = BranchAdapter(it, onCommitClick = { data ->

                    val model = CommitLiveData(
                        arguments?.getString("ownerName").toString(),
                        arguments?.getString("repoName").toString(),
                        data.name
                    )
                    branchViewModel?.openCommit(model)

                })

            }
        })
    }

    private fun observeOpenCommit() {
        branchViewModel?.openCommitEvent?.observe(requireActivity(), {

            val intent = Intent(requireActivity(), CommitActivity::class.java)
            intent.putExtra(
                "OwnerName", it.ownerName
            )
            intent.putExtra(
                "RepoName", it.repoName
            )
            intent.putExtra("SHA", it.branchName)
            startActivity(intent)
        })
    }

}