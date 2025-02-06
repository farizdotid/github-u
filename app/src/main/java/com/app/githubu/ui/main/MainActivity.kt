package com.app.githubu.ui.main

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityMainBinding
import com.app.githubu.model.content.User
import com.app.githubu.model.entities.LastViewUser
import com.app.githubu.ui.adapter.LastUserViewAdapter
import com.app.githubu.ui.adapter.PagingLoadStateAdapter
import com.app.githubu.ui.adapter.UserPagingAdapter
import com.app.githubu.ui.adapter.UserSearchAdapter
import com.app.githubu.ui.detail.UserDetailActivity
import com.app.githubu.utils.afterTextChangedDebounce
import com.app.githubu.utils.gone
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel:MainViewModel by viewModels()

    private lateinit var userAdapter: UserPagingAdapter
    private lateinit var userSearchAdapter: UserSearchAdapter
    private lateinit var lastUserViewAdapter: LastUserViewAdapter

    override fun initialize() {
        setupUserAdapter()
        fetchUsers()
    }

    override fun initObserveViewModel() {
        mainViewModel.userList.observe(this) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbLoading.gone()
                    initUserSearchAdapter(result.data ?: arrayListOf())
                }

                Result.Status.ERROR -> {
                    binding.pbLoading.gone()
                    notify(result.message.toString())
                }

                Result.Status.LOADING -> {
                    binding.pbLoading.visible()
                }
            }
        }
    }

    private fun fetchUsers() {
        lifecycleScope.launch {
            mainViewModel.pagedUsers.collectLatest { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
    }

    override fun initAction() {
        binding.etSearch.afterTextChangedDebounce(800, input = {
            val keyword = it
            if (keyword.isEmpty()) {
                mainViewModel.requestUsers()
            } else {
                mainViewModel.requestSearchUsers(keyword)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            initLastUserViewAdapter(mainViewModel.getAllDataViewedUsers() as ArrayList<LastViewUser>)
        }
    }

    private fun setupUserAdapter() {
        userAdapter = UserPagingAdapter().apply {
            setOnItemClickCallback(object : UserPagingAdapter.UserAdapterCallback {
                override fun onClicked(data: User) {
                    UserDetailActivity.start(this@MainActivity, false, data.username)
                }

            })
        }
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter { userAdapter.retry() }
            )
        }
    }

    private fun initUserSearchAdapter(userList: ArrayList<User>) {
        userSearchAdapter = UserSearchAdapter(userList).apply {
            setOnItemClickCallback(object : UserSearchAdapter.UserSearchAdapterCallback {
                override fun onClicked(user: User) {
                    UserDetailActivity.start(this@MainActivity, false, user.username)
                }

            })
        }
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userSearchAdapter
        }

        if (userList.isEmpty()) {
            notify("User not found")
        }
    }

    private fun initLastUserViewAdapter(userViewList: ArrayList<LastViewUser>) {
        lastUserViewAdapter = LastUserViewAdapter(userViewList)
        binding.rvUserViewed.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lastUserViewAdapter
        }
    }

}