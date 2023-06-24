package com.example.docseeker

import Beans.Reviews
import Interface.ReviewsService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ReviewsDoctor : AppCompatActivity() {
    private lateinit var reviewsAdapter : ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews_doctor)

        val doctorReviewName = findViewById<TextView>(R.id.doctorReviewName)
        val doctorReviewArea = findViewById<TextView>(R.id.doctorReviewArea)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerReviews)
        recyclerView.layoutManager= LinearLayoutManager(this)
        reviewsAdapter = ReviewsAdapter(emptyArray())
        recyclerView.adapter = reviewsAdapter

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val doctorId = sharedPref.getString("id", "X")

        GlobalScope.launch(Dispatchers.Main){
            val arrayOfReviews = getReviewsByDoctorId(doctorId.toString().toInt())
            reviewsAdapter.updateReviews(arrayOfReviews)
        }


        doctorReviewName.text = sharedPref.getString("name", "Doctor Usuario")
        doctorReviewArea.text = sharedPref.getString("speciality", "DOCTOR")

        // FUNCTIONS TO EVERY ACTIVITY WHICH USES TOOLBAR
        val toolbarClickListener = DoctorToolbarClickListener(this)

        // REFERENCES TO BUTTONS FROM TOOLBAR
        val button1 = findViewById<ImageButton>(R.id.button1Doctor)
        val button2 = findViewById<ImageButton>(R.id.button2Doctor)
        val button3 = findViewById<ImageButton>(R.id.button3Doctor)
        val button4 = findViewById<ImageButton>(R.id.button4Doctor)


        // SET OnClickListener WITH ToolbarClickListener
        button1.setOnClickListener(toolbarClickListener)
        button2.setOnClickListener(toolbarClickListener)
        button3.setOnClickListener(toolbarClickListener)
    }

    suspend fun getReviewsByDoctorId(idDoctor: Int): Array<Reviews> {
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(ReviewsService::class.java)
        return withContext(Dispatchers.IO) {
            val response = newsService.getReviewsByDoctorId(idDoctor).execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}