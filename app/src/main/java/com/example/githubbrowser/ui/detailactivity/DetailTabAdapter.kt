package com.example.githubbrowser.ui.detailactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubbrowser.ui.branchfragment.BranchFragment
import com.example.githubbrowser.ui.issuefragment.IssueFragment
import javax.inject.Inject

class DetailTabAdapter @Inject constructor(
    private val owner: String,
    private val repositoryName: String,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val TOTAL_TABS = 2
        private const val POSITION_BRANCH_TAB = 0
    }

    override fun getItemCount() = TOTAL_TABS

    override fun createFragment(position: Int): Fragment {
        return if (position == POSITION_BRANCH_TAB) {
            BranchFragment.newInstance(
                owner,
                repositoryName
            )
        } else {
            IssueFragment.newInstance(
                owner,
                repositoryName
            )
        }
    }
}
