package com.example.docseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text

class ReviewsPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews_patient)

        val doctorReviewName = findViewById<TextView>(R.id.doctorReviewName)
        val doctorReviewArea = findViewById<TextView>(R.id.doctorReviewArea)
        val btnComment = findViewById<Button>(R.id.btnComment)

        val doctorId = intent.getStringExtra("doctorId")
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
    }
}