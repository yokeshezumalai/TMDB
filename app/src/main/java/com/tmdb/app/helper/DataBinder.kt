
package com.tmdb.app.helper

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("customLoadImage")
fun loadImage(imageView: ImageView, image: String?) {
    image?.let { imageView.loadUrl(it) }
}

@BindingAdapter("customVisibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}


