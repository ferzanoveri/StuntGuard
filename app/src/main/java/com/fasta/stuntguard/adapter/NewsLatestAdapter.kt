package com.fasta.stuntguard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fasta.stuntguard.data.response.News
import com.fasta.stuntguard.databinding.ItemNewsLatestBinding

class NewsLatestAdapter(private val listNews: ArrayList<News>, private val limit: Int = listNews.size) : RecyclerView.Adapter<NewsLatestAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemNewsLatestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemNewsLatestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (limit < listNews.size) limit else listNews.size
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]

        holder.binding.apply {
            genre.text = news.publisher
            date.text = news.date
            newsTitle.text = news.title
            Glide.with(imgItemNews.context)
                .load(news.image)
                .into(imgItemNews)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(news)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(news : News)
    }
}