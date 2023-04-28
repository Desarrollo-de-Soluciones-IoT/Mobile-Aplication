package com.example.docseeker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UserSelection: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_selection)
    }

    fun goToPatientLogin() {
        val intent = Intent(this, Patients_LogIn::class.java)
        startActivity(intent)
    }
}