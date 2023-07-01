package com.example.docseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class UserSelectionRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_selection_register)
    }

    fun goToPatientRegister(view: View) {
        val intent = Intent(this, PatientsRegister::class.java)
        startActivity(intent)
    }
    fun goToDoctorRegister(view: View) {
        val intent = Intent(this, Doctors_LogIn::class.java)
        startActivity(intent)
    }
}