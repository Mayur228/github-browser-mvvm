package com.example.githubbrowser.ui.branchfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.model.CommitLiveData
import com.example.githubbrowser.ui.commitactivity.CommitActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchFragment : Fragment() {
    companion object {
        private val TAG: String = BranchFragment::class.java.simpleName
        val OWNER_NAME: String = "$TAG.OWNER_NAME"
        val REPO_NAME: String = "$TAG.REPO_NAME"

        fun newInstance(owner: String, repositoryName: String): BranchFragment {
            val args = Bundle()
            args.putString(OWNER_NAME, owner)
            args.putString(REPO_NAME, repositoryName)
            val fragment = BranchFragment()
            fragment.arguments = args
            return fragment
        }
    }


    private var branchNAme: TextView? = null
    private var branchRV: RecyclerView? = null

    private val branchViewModel: BranchViewModel by viewModels()

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

        branchViewModel.getBranch(
            arguments?.getString(OWNER_NAME).toString(),
            arguments?.getString(REPO_NAME).toString()
        )
    }

    private fun observeBranchData() {
        branchViewModel.data.observe(requireActivity(), {

            if (it.isEmpty()) {
                branchNAme?.visibility = View.VISIBLE
                branchNAme?.text = ("No Branches")
            } else {
                branchNAme?.visibility = View.GONE

                branchRV?.adapter = BranchAdapter(it, onCommitClick = { data ->

                    val model = CommitLiveData(
                        arguments?.getString(OWNER_NAME).toString(),
                        arguments?.getString(REPO_NAME).toString(),
                        data.name
                    )
                    branchViewModel.openCommit(model)

                })

            }
        })
    }

    private fun observeOpenCommit() {
        branchViewModel.openCommitEvent.observe(requireActivity(), {

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