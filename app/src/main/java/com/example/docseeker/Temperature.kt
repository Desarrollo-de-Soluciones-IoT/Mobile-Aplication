package com.example.docseeker

import Beans.Patients
import Interface.PatientsService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Temperature : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
        val sharedPreferences = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val userType = sharedPreferences.getString("userType", "valor_por_defecto")
        val nameView = findViewById<TextView>(R.id.patientName)

        if (userType == "PATIENT") {
            // Hacer algo con el valorString
            nameView.text = sharedPreferences.getString("name", "valor_por_defecto")
            Log.d("TAG", "Valor obtenido: $userType")
        } else {
            val patientId = intent.getStringExtra("patientId")
            Log.d("TAG", "ID PATIENT: $patientId")

            GlobalScope.launch(Dispatchers.Main){
                val patient = getPatientById(patientId.toString().toInt())
                nameView.text = patient?.name.toString()
            }
        }

    }
    suspend fun getPatientById(patientId: Int): Patients? {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val patientsService = retrofit.create(PatientsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = patientsService.getPatientsById(patientId).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}