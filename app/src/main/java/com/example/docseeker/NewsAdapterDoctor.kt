package com.example.docseeker

import Beans.News
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapterDoctor(var news: Array<News>) : RecyclerView.Adapter<NewsAdapterDoctor.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.findViewById(R.id.newTitle)
        val imageView: ImageView = itemView.findViewById(R.id.newImage)
        val cardView: CardView = itemView.findViewById(R.id.newCard)
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

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewDetailsDoctor::class.java)
            intent.putExtra("titleNew", new.title)
            intent.putExtra("imageUrlNew", new.imageUrl)
            intent.putExtra("descriptionNew", new.description)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateNews(newNewsList: Array<News>) {
        news = newNewsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return news.size
    }
}