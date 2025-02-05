package com.app.githubu.ui.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.app.githubu.base.BaseActivity
import com.app.githubu.databinding.ActivityUserDetailBinding
import com.app.githubu.model.content.UserDetail
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

@AndroidEntryPoint
class UserDetailActivity :
    BaseActivity<ActivityUserDetailBinding>(ActivityUserDetailBinding::inflate) {

    private val userDetailViewModel: UserDetailViewModel by viewModels()

    private var paramUsername = ""
    private var userDetail: UserDetail? = null

    override fun loadBundleExtra() {
        super.loadBundleExtra()

        paramUsername = intent.getStringExtra(EXTRA_USERNAME).orEmpty()
        userDetailViewModel.requestDetailUser(paramUsername)
    }

    override fun initialize() {
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

        userDetailViewModel.userRepos.observe(this) { result ->
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
        binding.ivBack.setSafeOnClickListener { finish() }

        binding.ivBlog.setSafeOnClickListener {
            userDetail?.blog?.asUri()?.openInBrowser(this)
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