package com.am.taskarticles.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.am.taskarticles.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth =10f
        centerRadius =50f
        start()
    }
}

fun ImageView.loadImage(uri:String?,circularProgressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(circularProgressDrawable).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(uri).into(this)
}

