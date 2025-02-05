package com.app.githubu.ui.main

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityMainBinding
import com.app.githubu.ui.adapter.PagingLoadStateAdapter
import com.app.githubu.ui.adapter.UserPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var userAdapter: UserPagingAdapter

    override fun initialize() {
        setupUserAdapter()
    }

    override fun initObserveViewModel() {
        lifecycleScope.launch {
            mainViewModel.pagedUsers.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
    }

    override fun initAction() {
    }

    private fun setupUserAdapter() {
        userAdapter = UserPagingAdapter()
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { userAdapter.retry() },
                footer = PagingLoadStateAdapter { userAdapter.retry() }
            )
        }
    }

}