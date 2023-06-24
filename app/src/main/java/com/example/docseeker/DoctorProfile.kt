package com.example.docseeker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class DoctorProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile)

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.doctorProfileName)
        val speciality = findViewById<TextView>(R.id.doctorProfileSpeciality)
        val nPatients = findViewById<TextView>(R.id.doctorProfilePatients)
        val description = findViewById<TextView>(R.id.doctorProfileDescription)
        val age = findViewById<TextView>(R.id.doctorProfileAge)
        val exp = findViewById<TextView>(R.id.doctorProfileYearsExp)
        val price = findViewById<TextView>(R.id.doctorProfilePrice)
        val btnEdit = findViewById<Button>(R.id.doctorProfileButtonEdit)
        val btnReviews = findViewById<Button>(R.id.doctorProfileButtonReview)

        name.text = sharedPref.getString("name", "Doctor Usuario")
        speciality.text = sharedPref.getString("speciality", "DOCTOR")
        nPatients.text = sharedPref.getString("patients_assisted", "X")
        description.text = sharedPref.getString("description", "HOLA")
        age.text = sharedPref.getString("age", "X")
        exp.text = sharedPref.getString("experience_years", "X")
        price.text = sharedPref.getString("doctor_fee", "X")


        btnEdit.setOnClickListener{ v ->
            val intent = Intent(this, DoctorProfileEdit::class.java)
            startActivity(intent)
        }
        btnReviews.setOnClickListener{ v ->
            val intent = Intent(this, ReviewsDoctor::class.java)
            startActivity(intent)
        }


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
}