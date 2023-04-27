package com.example.docseeker

import Beans.News
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CarouselRVAdapter(private val carouselDataList: Array<News>) :
    RecyclerView.Adapter<CarouselRVAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {

        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageview)
        val textView = holder.itemView.findViewById<TextView>(R.id.textview)
        Glide.with(holder.itemView)
            .load(carouselDataList[position].imageUrl)
            .into(imageView)
        textView.text = carouselDataList[position].title
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}