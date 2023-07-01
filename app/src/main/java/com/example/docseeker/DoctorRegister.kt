package com.example.docseeker

import Beans.Doctors
import Beans.Patients
import Interface.DoctorsService
import Interface.PatientsService
import Interface.ReviewsService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DoctorRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)

        var txtName = findViewById<EditText>(R.id.nameInputRegisterDoctor)
        var txtAge = findViewById<EditText>(R.id.ageInputRegisterDoctor)
        var txtDni = findViewById<EditText>(R.id.dniInputRegisterDoctor)
        var txtPrice = findViewById<EditText>(R.id.costInputRegisterDoctor)
        var txtSpeciality = findViewById<EditText>(R.id.specialityInputRegisterDoctor)
        var txtEmail = findViewById<EditText>(R.id.emailInputRegisterDoctor)
        var txtPassword = findViewById<EditText>(R.id.passwordInputRegisterDoctor)
        var btnRegister = findViewById<Button>(R.id.RegisterButtonDoctor)

        GlobalScope.launch(Dispatchers.Main) {

            val sizeDoctors = getDoctorsSize()
            btnRegister.setOnClickListener() {
                var doctor = Doctors(
                    id = sizeDoctors,
                    age = txtAge.text.toString().toInt(),
                    dni = txtDni.text.toString(),
                    email = txtEmail.text.toString(),
                    name = txtName.text.toString(),
                    password = txtPassword.text.toString(),
                    userType = 1,
                    description = "",
                    doctorFee = txtPrice.text.toString().toFloat(),
                    experienceYears = 0,
                    patientsAssisted = 0,
                    profilePhoto = "",
                    speciality = txtSpeciality.text.toString()
                )
                createDoctor(doctor)
                val intent = Intent(this@DoctorRegister, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    suspend fun getDoctorsSize(): Int? {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val doctorService = retrofit.create(DoctorsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = doctorService.getDoctors().execute()
            if (response.isSuccessful) {
                (response.body()?.size ?: 0) + 1
            } else {
                null
            }
        }
    }

    fun createDoctor(doctor: Doctors) {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val doctorsService = retrofit.create(DoctorsService::class.java)


        val call = doctorsService.createDoctor(doctor)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("DOCTOR", "DOCTOR CREADO CON EXITO")
                } else {
                    // Ocurrió un error al crear la cita
                    Log.d("DOCTOR", "ALGO SALIÓ MAL")
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