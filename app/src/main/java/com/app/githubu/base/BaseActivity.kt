package com.app.githubu.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.githubu.utils.allowInfiniteLines
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VB : ViewBinding>(val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        loadBundleExtra()
        initialize()
        initObserveViewModel()
        initAction()
    }

    open fun loadBundleExtra() = Unit
    abstract fun initialize()
    abstract fun initObserveViewModel()
    abstract fun initAction()

    internal fun notify(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .allowInfiniteLines()
            .show()
}