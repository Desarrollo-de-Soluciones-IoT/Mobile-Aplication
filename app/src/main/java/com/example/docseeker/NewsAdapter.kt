package com.example.docseeker

import Beans.News
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(var news: Array<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.findViewById(R.id.newTitle)
        val imageView: ImageView = itemView.findViewById(R.id.newImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_card, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val new = news[position]
        holder.titleView.text = new.title
        Glide.with(holder.itemView)
            .load(new.imageUrl)
            .into(holder.imageView)
    }

    fun updateNews(newNewsList: Array<News>) {
        news = newNewsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return news.size
    }
}