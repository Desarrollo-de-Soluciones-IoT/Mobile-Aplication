package com.example.docseeker

import Beans.Patients
import Interface.PatientsService
import Interface.PulseService
import Interface.TemperatureService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Pulse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulse)
        val sharedPreferences = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val userType = sharedPreferences.getString("userType", "valor_por_defecto")
        val nameView = findViewById<TextView>(R.id.patientName)
        val currentTemperatureView = findViewById<TextView>(R.id.currentTemperature)
        val temperature1View = findViewById<TextView>(R.id.temperature1)
        val temperature2View = findViewById<TextView>(R.id.temperature2)
        val temperature3View = findViewById<TextView>(R.id.temperature3)
        GlobalScope.launch(Dispatchers.Main){
            val temperatures = getPulses()
            val lastFiveNews = temperatures.takeLast(5).toTypedArray()

            if (temperatures.isNotEmpty()) {
                // Acceder al último elemento del array
                val ultimoElemento = temperatures.last()
                val penultimoElemento = temperatures.getOrElse(temperatures.size - 2) { null }
                val antepenultimoElemento = temperatures.getOrElse(temperatures.size - 3) { null }

                /*val floatValue1: Float = ultimoElemento.value.toString().toFloat()
                val floatValue2: Float = penultimoElemento?.value.toString().toFloat()
                val floatValue3: Float = antepenultimoElemento?.value.toString().toFloat()*/

                currentTemperatureView.text = ultimoElemento.value.toString() + "°C"
                temperature1View.text = ultimoElemento.value.toString() + "°C"
                temperature2View.text = penultimoElemento?.value.toString() + "°C"
                temperature3View.text = antepenultimoElemento?.value.toString() + "°C"

            } else {
                println("El array está vacío")
            }
        }
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
    suspend fun getPulses(): Array<Beans.Pulse>{

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pulseService = retrofit.create(PulseService::class.java)

        return withContext(Dispatchers.IO) {
            val response = pulseService.getPulses().execute()
            if (response.isSuccessful) {
                Log.d("PULSES", "CARGO TODO BIEN")
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}