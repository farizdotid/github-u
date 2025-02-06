package com.app.githubu.ui.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityUserDetailBinding
import com.app.githubu.model.content.User
import com.app.githubu.model.content.UserDetail
import com.app.githubu.model.content.UserRepo
import com.app.githubu.ui.adapter.PagingLoadStateAdapter
import com.app.githubu.ui.adapter.UserPagingAdapter
import com.app.githubu.ui.adapter.UserRepoPagingAdapter
import com.app.githubu.utils.asUri
import com.app.githubu.utils.gone
import com.app.githubu.utils.image.loadUrlCirle
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.openInBrowser
import com.app.githubu.utils.orDash
import com.app.githubu.utils.orZero
import com.app.githubu.utils.setSafeOnClickListener
import com.app.githubu.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailActivity :
    BaseActivity<ActivityUserDetailBinding>(ActivityUserDetailBinding::inflate) {

    private val userDetailViewModel: UserDetailViewModel by viewModels()
    private lateinit var userRepoPagingAdapter: UserRepoPagingAdapter

    private var paramUsername = ""
    private var userDetail: UserDetail? = null

    override fun loadBundleExtra() {
        super.loadBundleExtra()

        paramUsername = intent.getStringExtra(EXTRA_USERNAME).orEmpty()
        userDetailViewModel.requestDetailUser(paramUsername)
        userDetailViewModel.setUsername(paramUsername)
    }

    override fun initialize() {
        setupUserRepoAdapter()
    }

    override fun initObserveViewModel() {
        userDetailViewModel.userDetail.observe(this) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.pbLoading.gone()

                    userDetail = result.data

                    if (userDetail?.blog == "-") binding.ivBlog.gone()

                    "@${userDetail?.username?.orDash()}".let { text ->
                        binding.tvUsername.text = text
                    }
                    binding.ivAvatar.loadUrlCirle(userDetail?.avatar.orEmpty())
                    binding.tvName.text = userDetail?.name.orDash()
                    binding.tvLocation.text = userDetail?.location.orDash()
                    binding.tvTotalFollower.text = userDetail?.totalFollower.orZero().toString()
                    binding.tvTotalFollowing.text = userDetail?.totalFollowing.orZero().toString()
                    binding.tvTotalRepo.text = userDetail?.totalRepo.orZero().toString()
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

        fetchUserRepos()

//        userDetailViewModel.userRepos.observe(this) { result ->
//            when (result.status) {
//                Result.Status.SUCCESS -> {
//                    binding.pbLoading.gone()
//                }
//
//                Result.Status.ERROR -> {
//                    binding.pbLoading.gone()
//                    notify(result.message.toString())
//                }
//
//                Result.Status.LOADING -> {
//                    binding.pbLoading.visible()
//                }
//            }
//        }
    }

    override fun initAction() {
        binding.ivBack.setSafeOnClickListener { finish() }

        binding.ivBlog.setSafeOnClickListener {
            userDetail?.blog?.asUri()?.openInBrowser(this)
        }
    }

    private fun setupUserRepoAdapter() {
        userRepoPagingAdapter = UserRepoPagingAdapter().apply {
            setOnItemClickCallback(object:UserRepoPagingAdapter.UserRepoAdapterCallback{
                override fun onClicked(userRepo: UserRepo) {
                    userRepo.repoUrl.asUri().openInBrowser(this@UserDetailActivity)
                }

            })
        }
        binding.rvRepos.apply {
            layoutManager = LinearLayoutManager(this@UserDetailActivity)
            adapter = userRepoPagingAdapter.withLoadStateFooter(
                footer = PagingLoadStateAdapter { userRepoPagingAdapter.retry() }
            )
        }

        userRepoPagingAdapter.addLoadStateListener { loadState ->
            binding.pbLoading.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    private fun fetchUserRepos() {
        lifecycleScope.launch {
            userDetailViewModel.pagedUserRepos.collectLatest { pagingData ->
                userRepoPagingAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        private const val EXTRA_IS_CLEAR_BACK_STACK = "extra_is_clear_back_stack"
        private const val EXTRA_USERNAME = "extra_username"

        fun start(context: Context, isClearBackStack: Boolean, username: String) {
            val starter = Intent(context, UserDetailActivity::class.java)
            starter.putExtra(EXTRA_IS_CLEAR_BACK_STACK, isClearBackStack)
            starter.putExtra(EXTRA_USERNAME, username)

            if (isClearBackStack) {
                starter.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

            context.startActivity(starter)
        }
    }

}