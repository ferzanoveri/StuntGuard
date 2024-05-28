package com.example.stuntguard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stuntguard.data.response.news.News
import com.example.stuntguard.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: ArrayList<News>, private val limit: Int = listNews.size) :
    RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    class ListViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (limit < listNews.size) limit else listNews.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]

        holder.binding.apply {
            genre.text = news.publisher
            deskripsiNews.text = news.title
            Glide.with(imgItemNews.context)
                .load(news.image)
                .fitCenter()
                .centerCrop()
                .into(imgItemNews)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(news)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news: News)
    }
}