package com.example.docseeker

import Beans.Appointment
import Beans.News
import Beans.Reviews
import Interface.AppointmentsService
import Interface.NewsService
import Interface.ReviewsService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAppointments : AppCompatActivity() {
    private lateinit var appointmentsAdapter : AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_appointments)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerAppointmentsPatient)
        recyclerView.layoutManager= LinearLayoutManager(this)
        appointmentsAdapter = AppointmentAdapter(emptyArray())
        recyclerView.adapter = appointmentsAdapter

        GlobalScope.launch(Dispatchers.Main){
            val patientId = (sharedPref.getString("id", "PatientID")).toString().toInt()
            val arrayOfAppointments = getAppointmentsByPatientId(patientId)
            //val arrayOfAppointments = getAppointments()

            appointmentsAdapter.updateAppointments(arrayOfAppointments)
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

    suspend fun getAppointments(): Array<Appointment>{

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(AppointmentsService::class.java)


        return withContext(Dispatchers.IO) {
            val response = newsService.getAppointments().execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
    suspend fun getAppointmentsByPatientId(patientId: Int): Array<Appointment> {
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            //.baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
            //CONNECT TO LOCALHOST
            .baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(AppointmentsService::class.java)
        return withContext(Dispatchers.IO) {
            val response = newsService.getAppointmentsByPatientId(patientId).execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}