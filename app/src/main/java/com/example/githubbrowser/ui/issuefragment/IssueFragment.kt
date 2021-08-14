package com.example.githubbrowser.ui.issuefragment

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

class IssueFragment : Fragment() {

    var txt: TextView? = null
    var issueview: RecyclerView? = null

    var issueViewModel: IssueViewModel? = null

    var page:Int=1

    fun newInstance(): IssueFragment {
        return IssueFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.issuefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp(view)
        observeIssueData()
    }

    private fun setUp(view: View) {
        txt = view.findViewById(R.id.issue)

        issueview = view.findViewById(R.id.issue_view)

        issueview?.layoutManager =
            LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        issueViewModel = ViewModelProvider(this, MyViewModelFactory).get(IssueViewModel::class.java)

        issueview?.adapter=IssueAdapter(listOf(),requireActivity().applicationContext)

        issueViewModel?.getIssue(
            arguments?.getString("OwnerName").toString(),
            arguments?.getString("RepoName").toString(),
            page
        )

//        issueview?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if ((issueview?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()==(issueview?.adapter as IssueAdapter).list.size-1){
//                    issueViewModel?.getIssue(
//                        arguments?.getString("OwnerName").toString(),
//                        arguments?.getString("RepoName").toString(),
//                        page++
//                    )
//                }
//            }
//        })

    }

    private fun observeIssueData() {
        issueViewModel?.data?.observe(requireActivity(), {

            if (it.isEmpty()) {
                txt?.text = ("No Issues")
            } else {
//                issueview?.adapter = IssueAdapter(it, requireActivity().applicationContext)
                (issueview?.adapter as IssueAdapter).list= it

            }
        }
        )
    }

}