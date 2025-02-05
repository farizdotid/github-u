package com.app.githubu.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.githubu.R
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityMainBinding
import com.app.githubu.utils.gone
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.visible

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mainViewModel:MainViewModel by viewModels()

    override fun initialize() {
        mainViewModel.requestUsers()
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