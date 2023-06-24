package com.example.docseeker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class DoctorProfileEdit : AppCompatActivity() {
    private lateinit var price:TextView
    private lateinit var description:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_profile_edit)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val name = findViewById<TextView>(R.id.doctorProfileEditName)
        val speciality = findViewById<TextView>(R.id.doctorProfileEditSpeciality)
        val nPatients = findViewById<TextView>(R.id.doctorProfileEditPatients)
        description = findViewById<TextView>(R.id.doctorProfileEditDescription)
        price = findViewById<TextView>(R.id.doctorProfileEditPrice)
        val btnSave = findViewById<Button>(R.id.doctorProfileEditButtonSave)

        name.text = sharedPref.getString("name", "Doctor Usuario")
        speciality.text = sharedPref.getString("speciality", "DOCTOR")
        nPatients.text = sharedPref.getString("patients_assisted", "X")
        description.text = sharedPref.getString("description", "HOLA")
        price.text = sharedPref.getString("doctor_fee", "X")


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
        button4.setOnClickListener(toolbarClickListener)

    }

    fun goToProfileDoctor(view: View) {
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        if(!price.text.isBlank()){
            editor.putString("doctor_fee", price.text.toString())
        }
        if(!description.text.isBlank()) {
            editor.putString("description", description.text.toString())
        }

        editor.apply()

        val intent = Intent(this, DoctorProfile::class.java)
        startActivity(intent)
    }
}