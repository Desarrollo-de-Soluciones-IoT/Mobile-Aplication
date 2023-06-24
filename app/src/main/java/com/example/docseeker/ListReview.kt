package com.example.docseeker

import Beans.Reviews
import Interface.DoctorsService
import Interface.ReviewsService
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import com.example.docseeker.BaseUrl

class ListReview: AppCompatActivity() {
    private lateinit var reviewsAdapter : ReviewsAdapter

    override fun onCreate(savedInstanceState:Bundle ?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_reviews)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerReviews)
        recyclerView.layoutManager=LinearLayoutManager(this)
        reviewsAdapter = ReviewsAdapter(emptyArray())
        recyclerView.adapter = reviewsAdapter

        GlobalScope.launch(Dispatchers.Main){
            val arrayOfReviews = getReviews()
            reviewsAdapter.updateReviews(arrayOfReviews )
        }
        val toolbarClickListener = ToolbarClickListener(this)
        val button1 = findViewById<ImageButton>(R.id.button1)
        val button2 = findViewById<ImageButton>(R.id.button2)
        val button3 = findViewById<ImageButton>(R.id.button3)
        val button4 = findViewById<ImageButton>(R.id.button4)

        button1.setOnClickListener(toolbarClickListener)
        button2.setOnClickListener(toolbarClickListener)
        button3.setOnClickListener(toolbarClickListener)
        button4.setOnClickListener(toolbarClickListener)

    }

    suspend fun getReviews(): Array<Reviews> {
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(ReviewsService::class.java)
        return withContext(Dispatchers.IO) {
            val response = newsService.getReviews().execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}
