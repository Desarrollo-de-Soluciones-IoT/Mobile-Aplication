package com.example.docseeker

import Beans.Reviews
import Interface.ReviewsService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.docseeker.BaseUrl

class ReviewsPatient : AppCompatActivity() {
    private lateinit var reviewsAdapter : ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews_patient)

        val doctorReviewName = findViewById<TextView>(R.id.doctorReviewName)
        val doctorReviewArea = findViewById<TextView>(R.id.doctorReviewArea)
        val btnComment = findViewById<Button>(R.id.btnComment)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerReviews)
        recyclerView.layoutManager= LinearLayoutManager(this)
        reviewsAdapter = ReviewsAdapter(emptyArray())
        recyclerView.adapter = reviewsAdapter

        val doctorId = intent.getStringExtra("doctorId")
        Log.d("REVIEWSPATIENT", intent.getStringExtra("doctorId").toString())

        GlobalScope.launch(Dispatchers.Main){
            val arrayOfReviews = getReviewsByDoctorId(intent.getStringExtra("doctorId").toString().toInt())
            reviewsAdapter.updateReviews(arrayOfReviews)
        }

        doctorReviewName.text = intent.getStringExtra("doctorName")
        doctorReviewArea.text = intent.getStringExtra("doctorArea")
        btnComment.setOnClickListener{
            val intent = Intent(this, NewReview::class.java)
            intent.putExtra("doctorId", doctorId)
            intent.putExtra("doctorName", doctorReviewName.text)
            intent.putExtra("doctorArea", doctorReviewArea.text)
            startActivity(intent)
        }

        // FUNCTIONS TO EVERY ACTIVITY WHICH USES TOOLBAR
        val toolbarClickListener = ToolbarClickListener(this)

        // REFERENCES TO BUTTONS FROM TOOLBAR
        val button1 = findViewById<ImageButton>(R.id.button1)
        val button2 = findViewById<ImageButton>(R.id.button2)
        val button3 = findViewById<ImageButton>(R.id.button3)
        val button4 = findViewById<ImageButton>(R.id.button4)


        // SET OnClickListener WITH ToolbarClickListener
        button1.setOnClickListener(toolbarClickListener)
        button2.setOnClickListener(toolbarClickListener)
        button3.setOnClickListener(toolbarClickListener)
        button4.setOnClickListener(toolbarClickListener)

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