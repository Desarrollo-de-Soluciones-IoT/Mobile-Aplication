package com.example.docseeker

import Beans.Doctors
import Beans.Prescriptions
import Interface.DoctorsService
import Interface.PrescriptionsService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrescriptionPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_patient)

        val doctorNameView: TextView = findViewById(R.id.doctorPrescriptionDoctorName)
        val doseView: TextView = findViewById(R.id.doctorPrescriptionDose)
        val medicineNameView: TextView = findViewById(R.id.doctorPrescriptionName)
        val durationView: TextView = findViewById(R.id.doctorPrescriptionDuration)
        val dateView: TextView = findViewById(R.id.doctorPrescriptionDate)

        val doctorId = intent.getStringExtra("doctorId")
        val medicineName = intent.getStringExtra("medicineName")
        val medicineDosage = intent.getStringExtra("medicineDosage")
        val medicineDuration = intent.getStringExtra("medicineDuration")
        val date = intent.getStringExtra("date")

        GlobalScope.launch(Dispatchers.Main){
            val doctor = getDoctorById(doctorId.toString().toInt())
            doctorNameView.text = doctor?.name.toString()
        }
        doseView.text = medicineDosage
        medicineNameView.text = medicineName
        durationView.text = "Duración de receta médica: " + medicineDuration
        dateView.text = "Registro receta médica: " + date

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
    suspend fun getDoctorById(doctorId: Int): Doctors? {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val doctorsService = retrofit.create(DoctorsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = doctorsService.getDoctorById(doctorId).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}