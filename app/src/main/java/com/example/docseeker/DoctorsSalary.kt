package com.example.docseeker

import Beans.Appointment
import Interface.AppointmentsService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DoctorsSalary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doctors_payment)

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val salary = findViewById<TextView>(R.id.salaryNumber)

        GlobalScope.launch(Dispatchers.Main){
            val doctorId = (sharedPref.getString("id", "DoctorID")).toString().toInt()
            val arrayOfAppointments = getAppointmentsByDoctorId(doctorId)
            salary.text = "S/ " + (arrayOfAppointments.size * (sharedPref.getString("doctor_fee", "X")).toString().toInt()).toString()

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

    suspend fun getAppointmentsByDoctorId(doctorId: Int): Array<Appointment> {
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(AppointmentsService::class.java)
        return withContext(Dispatchers.IO) {
            val response = newsService.getAppointmentsByDoctorId(doctorId).execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}