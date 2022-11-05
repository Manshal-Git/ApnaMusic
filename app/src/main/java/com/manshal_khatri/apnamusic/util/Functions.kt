package com.manshal_khatri.apnamusic.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.Glide

object Functions {

    fun setImage(imgUrl : String,imageView : ImageView,){
        Glide.with(imageView.context).load(imgUrl).centerCrop().into(imageView)
    }
    fun makeViewVisible(v : View){
        v.visibility = VISIBLE
    }
    fun makeViewGone(v : View){
        v.visibility = GONE
    }
}