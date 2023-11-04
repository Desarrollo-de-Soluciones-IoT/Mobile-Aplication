package com.example.docseeker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.cardview.widget.CardView

class ListServices : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_services)

        val temperatureButton = findViewById<CardView>(R.id.temperature_button)
        val sharedPreferences = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val userType = sharedPreferences.getString("userType", "valor_por_defecto")
        temperatureButton.setOnClickListener{
            val intent2 = Intent(this, Temperature::class.java)
            if(userType == "DOCTOR"){
                Log.d("TAG", "ES DOCTOR")

                val patientId = intent.getStringExtra("patientId")
                Log.d("TAG", "ID PATIENT: $patientId")
                intent2.putExtra("patientId", patientId.toString())

            }
            startActivity(intent2)
        }
        val pulseButton = findViewById<CardView>(R.id.pulse_button)
        pulseButton.setOnClickListener{
            val intent2 = Intent(this, Pulse::class.java)
            if(userType == "DOCTOR"){
                Log.d("TAG", "ES DOCTOR")
                val patientId = intent.getStringExtra("patientId")
                Log.d("TAG", "ID PATIENT: $patientId")
                intent2.putExtra("patientId", patientId.toString())

            }
            startActivity(intent2)
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
}