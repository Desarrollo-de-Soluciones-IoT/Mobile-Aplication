package com.example.docseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DoctorProfilePatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_patient)

        val doctorProfileImage = findViewById<ImageView>(R.id.doctorProfilemage)
        val doctorProfileName = findViewById<TextView>(R.id.doctorProfileName)
        val doctorProfileArea = findViewById<TextView>(R.id.doctorProfileArea)
        val doctorProfilePatients = findViewById<TextView>(R.id.doctorProfilePatients)
        val doctorProfileAge = findViewById<TextView>(R.id.doctorProfileAge)
        val doctorProfileExperience = findViewById<TextView>(R.id.doctorProfileExperience)
        val doctorProfilePrice = findViewById<TextView>(R.id.doctorProfilePrice)
        val doctorProfileDescription = findViewById<TextView>(R.id.doctorProfileDescription)
        val doctorProfileButton = findViewById<Button>(R.id.doctorProfileButton)

        val doctorId = intent.getStringExtra("doctorId")
        doctorProfileImage.setImageResource(R.drawable.user_type_health_professional)
        doctorProfileName.text = intent.getStringExtra("doctorName")
        doctorProfileArea.text = intent.getStringExtra("doctorArea")
        doctorProfilePatients.text = intent.getStringExtra("doctorNPatients")
        doctorProfileAge.text = intent.getStringExtra("doctorAge")
        doctorProfileExperience.text = intent.getStringExtra("doctorYears")
        doctorProfilePrice.text = intent.getStringExtra("doctorPrice")
        doctorProfileDescription.text = intent.getStringExtra("doctorDescription")

        doctorProfileButton.setOnClickListener{
            val intent = Intent(this, ReviewsPatient::class.java)
            intent.putExtra("doctorId", doctorId)
            intent.putExtra("doctorName", doctorProfileName.text)
            intent.putExtra("doctorArea", doctorProfileArea.text)
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