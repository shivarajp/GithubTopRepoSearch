package com.shivaraj.tilaapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shivaraj.tilaapp.data.local.RepoModel
import com.shivaraj.tilaapp.databinding.TaskItemBinding

class RepoAdapter(private val repos: ArrayList<RepoModel> , val listener: OnRowClickListener) :
    RecyclerView.Adapter<RepoAdapter.ItemHolder>() {

    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(img : ImageView, url: String?){
            Glide.with(img.context)
                .load(url).apply(RequestOptions().circleCrop())
                .into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(repos[position], position)
    }

    inner class ItemHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoModel, position: Int) {
            binding.repo = item
            binding.imageUrl = item.url
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    fun addTasks(songModel: ArrayList<RepoModel>) {
        repos.addAll(songModel)
        notifyDataSetChanged()
    }

    fun clear() {
        repos.clear()
        notifyDataSetChanged()
    }
}