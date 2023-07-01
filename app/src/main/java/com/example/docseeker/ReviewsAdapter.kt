package com.example.docseeker

import Beans.Doctors
import Beans.Reviews
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ReviewsAdapter (var reviews: Array<Reviews>) : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>(){
    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView: ImageView = itemView.findViewById(R.id.ReviewImg)
        val reviewCardView:CardView=itemView.findViewById(R.id.RCard)
        val textView: TextView = itemView.findViewById(R.id.ReviewName)
        val textView2: TextView = itemView.findViewById(R.id.ReviewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_card, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        holder.textView.text=review.patientName
        holder.textView2.text = review.description
        //holder.imageView.setImageResource(R.drawable.paciente_profile)


        /*Glide.with(holder.itemView)
            .load(doctor.imageUrl)
            .into(holder.imageView)*/

    }

    fun updateReviews(newReviewsList: Array<Reviews>) {
        reviews = newReviewsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return reviews.size
    }
}