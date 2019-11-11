package com.example.diliptestapp.ui.main

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.diliptestapp.R
import com.example.diliptestapp.ui.zoom.ZoomActivity
import com.example.diliptestapp.util.PicassoTrustAll


object ImageUrlBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:img")
    fun setImageUrl(view: ImageView, url: String) {
        val strs = url.split("#").toTypedArray()
        PicassoTrustAll.getInstance(view.context)
            .load(strs[0])
            .placeholder(R.mipmap.ic_launcher)
            .into(view)

        view.setOnClickListener(View.OnClickListener {
            val intent = Intent(view.context, ZoomActivity::class.java)
            intent.putExtra("id", strs[1].toInt())
            intent.putExtra("imageUrl", strs[0])
            view.context.startActivity(intent)
        })

    }
}