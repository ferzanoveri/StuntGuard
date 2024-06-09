package com.fasta.stuntguard.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.fasta.stuntguard.data.response.News
import com.fasta.stuntguard.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    class ListViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]

        holder.binding.apply {
            genre.text = news.publisher
            newsTitle.text = news.title
            Glide.with(imgItemNews.context)
                .load(news.image)
                .transform(CircleCrop())
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