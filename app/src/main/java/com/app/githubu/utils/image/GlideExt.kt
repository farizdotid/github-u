package com.app.githubu.utils.image

import android.widget.ImageView
import com.app.githubu.R
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop

fun ImageView.loadUrl(source: Any) {
    GlideApp.with(this)
        .load(source)
        .transform(CenterCrop())
        .placeholder(R.drawable.no_image_512)
        .error(R.drawable.no_image_512)
        .into(this)
}

fun ImageView.loadUrlCirle(source: Any) {
    GlideApp.with(this)
        .load(source)
        .transform(CircleCrop())
        .placeholder(R.drawable.no_image_512)
        .error(R.drawable.no_image_512)
        .into(this)
}