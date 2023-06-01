package com.testinopenapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule


fun ImageView.loadUrl(url: String?) {
    Glide
        .with(this)
        .load(url)
        .into(this)
}