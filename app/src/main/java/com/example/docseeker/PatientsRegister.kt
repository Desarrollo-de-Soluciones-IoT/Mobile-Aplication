package com.example.docseeker

import Beans.Appointment
import Beans.Patients
import Beans.Reviews
import Interface.AppointmentsService
import Interface.PatientsService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.docseeker.BaseUrl

class PatientsRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_register)

        var txtName = findViewById<EditText>(R.id.nameInputRegisterPatient)
        var txtAge = findViewById<EditText>(R.id.ageInputRegisterPatient)
        var txtDni = findViewById<EditText>(R.id.dniInputRegisterPatient)
        var txtNumber = findViewById<EditText>(R.id.numberInputRegisterPatient)
        var txtBirthday = findViewById<EditText>(R.id.birthdayInputRegisterPatient)
        var txtEmail = findViewById<EditText>(R.id.emailInputRegisterPatient)
        var txtPassword = findViewById<EditText>(R.id.passwordInputRegisterPatient)
        var btnRegister = findViewById<Button>(R.id.RegisterButtonPatient)

        btnRegister.setOnClickListener(){
            var patient = Patients(
                id = 5,
                age = txtAge.text.toString().toInt(),
                dni = txtDni.text.toString(),
                email = txtEmail.text.toString(),
                name = txtName.text.toString(),
                bmi = null,
                height = null,
                weight = null,
                password = txtPassword.text.toString(),
                birthDate = txtBirthday.text.toString(),
                phoneNumber = txtNumber.text.toString(),
                userType = 0,
                reviews = emptyList(),
                allergies = emptyList()
            )
            createPatient(patient)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun createPatient(patient: Patients) {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val patientsService = retrofit.create(PatientsService::class.java)


        val call = patientsService.createPatient(patient)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("PATIENTS", "PACIENTE CREADO CON EXITO")
                } else {
                    // Ocurrió un error al crear la cita
                    Log.d("PATIENTS", "ALGO SALIÓ MAL")
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