package com.shivaraj.tilaapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shivaraj.tilaapp.R
import com.shivaraj.tilaapp.data.local.RepoModel
import com.shivaraj.tilaapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPO = "repo"

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(img: ImageView, url: String?) {
            Glide.with(img.context)
                .load(url).apply(RequestOptions().circleCrop())
                .into(img)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_details)

        val binding =
            DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        val repo = intent.getSerializableExtra(EXTRA_REPO) as RepoModel

        repo?.let {

            binding?.repo = it
        }

    }
}
