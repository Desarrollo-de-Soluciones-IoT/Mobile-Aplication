package com.example.docseeker

import Beans.Reviews
import Interface.ReviewsService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        val doctorReviewName = findViewById<TextView>(R.id.doctorReviewName)
        val doctorReviewArea = findViewById<TextView>(R.id.doctorReviewArea)
        val btnPublish = findViewById<Button>(R.id.btnPublish)

        val doctorId = intent.getStringExtra("doctorId")
        doctorReviewName.text = intent.getStringExtra("doctorName")
        doctorReviewArea.text = intent.getStringExtra("doctorArea")
        btnPublish.setOnClickListener{
            val intent = Intent(this, ReviewsPatient::class.java)
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
    }

    suspend fun getReviews(): Array<Reviews> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            //.baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
            //CONNECT TO LOCALHOST
            .baseUrl("http://192.168.18.9:8080/api/v1/")
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