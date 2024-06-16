package com.fasta.stuntguard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.fasta.stuntguard.data.response.News
import com.fasta.stuntguard.databinding.ItemNewsRelevansiBinding

class NewsRelevansiAdapter (private val listNewsRelevansi: ArrayList<News>) : RecyclerView.Adapter<NewsRelevansiAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemNewsRelevansiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRelevansiAdapter.ListViewHolder {
        val binding = ItemNewsRelevansiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsRelevansiAdapter.ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listNewsRelevansi.size
    override fun onBindViewHolder(holder: NewsRelevansiAdapter.ListViewHolder, position: Int) {
        val news = listNewsRelevansi[position]

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