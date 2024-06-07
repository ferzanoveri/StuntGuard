package com.fasta.stuntguard.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fasta.stuntguard.R
import com.fasta.stuntguard.data.model.NewsModel
import com.fasta.stuntguard.databinding.ItemNewsBinding

class NewsAdapter: ListAdapter<NewsModel, NewsAdapter.NewsViewHolder>(DiffCallback){
    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imgNews: ImageView = itemView.findViewById(R.id.img_item_news)
        fun bind(news: NewsModel) {
            binding.newsTitle.text = news.title
            if (!news.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(news.imageUrl)
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(imgNews)
            } else {
                imgNews.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_place_holder))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}