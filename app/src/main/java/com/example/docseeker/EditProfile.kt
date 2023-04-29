package com.example.docseeker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patients_edit)


        // FUNCTIONS TO EVERY ACTIVITY WHICH USES TOOLBAR
        val editProfileClickListener = ProfilePatients(this)

        // REFERENCES TO BUTTONS FROM TOOLBAR
        val button1 = findViewById<ImageButton>(R.id.button1)



        // SET OnClickListener WITH ToolbarClickListener
        button1.setOnClickListener(editProfileClickListener)

    }
}