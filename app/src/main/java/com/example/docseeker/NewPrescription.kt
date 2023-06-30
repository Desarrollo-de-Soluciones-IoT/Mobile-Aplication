package com.example.docseeker

import Beans.CreateReview
import Beans.Doctors
import Beans.Patients
import Beans.Prescriptions
import Interface.DoctorsService
import Interface.PatientsService
import Interface.PrescriptionsService
import Interface.ReviewsService
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewPrescription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prescription)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        val patientName = findViewById<TextView>(R.id.patientNewPrescriptionName)
        val medicine = findViewById<EditText>(R.id.patientNewPrescriptionMedicine)
        val doses = findViewById<EditText>(R.id.patientNewPrescriptionDoses)
        val duration = findViewById<EditText>(R.id.patientNewPrescriptionDuration)
        val date = findViewById<EditText>(R.id.patientNewPrescriptionDate)
        val publishButton = findViewById<Button>(R.id.patientNewPrescriptionButton)
        var contentMedicine = ""
        var contentDoses = ""
        var contentDuration = ""
        var contentDate = ""


        val patientId = intent.getStringExtra("patientId")

        GlobalScope.launch(Dispatchers.Main){
            val patient = getPatientById(patientId.toString().toInt())
            patientName.text = patient?.name.toString()
        }

        publishButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val sizePrescriptions = getPrescriptions()
                Log.d("SIZE ARRAY", sizePrescriptions.toString())
                var prescription = Prescriptions(
                    id = sizePrescriptions,
                    patientId = patientId.toString().toInt(),
                    doctorId = sharedPref.getString("id", "DoctorId").toString().toInt(),
                    medicineName = medicine.text.toString(),
                    medicineDosage = doses.text.toString(),
                    medicineDuration = duration.text.toString(),
                    date = date.text.toString(),
                    )
                createPrescription(prescription)

                val intent = Intent(this@NewPrescription, MyPatients::class.java)
                startActivity(intent)
            }

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
        button4.setOnClickListener(toolbarClickListener)

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
    suspend fun getPrescriptions(): Int? {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val prescriptionsService = retrofit.create(PrescriptionsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = prescriptionsService.getPrescriptions().execute()
            if (response.isSuccessful) {
                (response.body()?.size ?: 0) + 1
            } else {
                null
            }
        }
    }

    fun createPrescription(prescription: Prescriptions) {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val prescriptionService = retrofit.create(PrescriptionsService::class.java)
        val call = prescriptionService.createPrescriptions(prescription)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("PRESCRIPTION", "CREADO PRESCRIPTION CON EXITO")
                } else {
                    // Ocurrió un error al crear la cita
                    Log.d("PRESCRIPTION", "ALGO SALIÓ MAL")
                    val statusCode = response.code()
                    val errorMessage = response.message()
                    val errorBody = response.errorBody()?.string()

                    Log.e("API Error", "Status Code: $statusCode")
                    Log.e("API Error", "Error Message: $errorMessage")
                    Log.e("API Error", "Error Body: $errorBody")

                    // Manejar el error de acuerdo a tus necesidades
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Ocurrió un error de red u otro error relacionado con Retrofit
                // Manejar el error de acuerdo a tus necesidades
            }
        })
    }
}