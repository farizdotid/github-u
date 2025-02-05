package com.app.githubu.ui.main

import androidx.activity.viewModels
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityMainBinding
import com.app.githubu.ui.detail.UserDetailActivity
import com.app.githubu.utils.gone
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel:MainViewModel by viewModels()

    override fun initialize() {
//        mainViewModel.requestSearchUsers("farizdotid")
        UserDetailActivity.start(this, false, "farizdotid")
    }

    override fun initObserveViewModel() {
        mainViewModel.userList.observe(this) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbLoading.gone()
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

    override fun initAction() {
    }

}