package com.example.docseeker

import Beans.Patients
import Interface.PatientsService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch

class Patients_LogIn : AppCompatActivity() {
    lateinit var userLogged: Patients
    //var arrayOfPatients: Array<Patients>? = null // Inicializar la variable como null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_log_in)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }

        // SharedPreferences USER LOGGED
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val txtEmail: EditText =findViewById(R.id.emailInput)
        val txtPassword: EditText =findViewById(R.id.passwordInput)
        val txtComment: TextView =findViewById(R.id.txtComment)
        val btnLogIn: Button =findViewById(R.id.logInButton)


        //GETTING PATIENTS DATA FROM ENDPOINT
        GlobalScope.launch(Dispatchers.Main) {
            val arrayOfPatients = getPatients()

            btnLogIn.setOnClickListener(){
                val user:String=txtEmail.text.toString()
                val password:String=txtPassword.text.toString()
                for (patient in arrayOfPatients) {
                    if (patient.email == user && patient.password == password) {
                        userLogged = patient
                        // SAVE SharedPreferences
                        editor.putString("id", userLogged.id.toString())
                        editor.putString("age", userLogged.age.toString())
                        editor.putString("DNI", userLogged.dni)
                        editor.putString("email", userLogged.email)
                        editor.putString("name", userLogged.name)
                        editor.putString("password", userLogged.password)
                        Log.d("TESTEOO", userLogged.height.toString())
                        editor.putString("height", userLogged.height.toString())
                        editor.putString("weight", userLogged.weight.toString())
                        editor.putString("bmi", userLogged.bmi.toString())
                        editor.putString("birth_date", userLogged.birthDate)
                        editor.putString("phone_number", userLogged.phoneNumber)
                        editor.apply()

                        val intent= Intent(this@Patients_LogIn, DashboardPatients::class.java)
                        startActivity(intent)
                        break
                    }
                    else{
                        txtEmail.text.clear()
                        txtPassword.text.clear()
                        txtComment.text="Usuario y/o password incorrectos"
                    }
                }
            }
        }
    }

    suspend fun getPatients(): Array<Patients> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(PatientsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = newsService.getPatients().execute()
            if (response.isSuccessful) {
                Log.d("LOGIN", "CARGO TODO BIEN")
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }

        /*var arrayOfPatients: Array<Patients>? = null
        val latch = CountDownLatch(1)

        newsService.getPatients().enqueue(object : Callback<List<Patients>> {
            override fun onResponse(call: Call<List<Patients>>, response: Response<List<Patients>>) {
                if (response.isSuccessful) {
                    val patientsList = response.body()
                    arrayOfPatients = patientsList?.toTypedArray()
                } else {
                }
                    latch.countDown()
            }
            override fun onFailure(call: Call<List<Patients>>, t: Throwable) {
                latch.countDown()
            }
        })
        latch.await()
        return arrayOfPatients
    }*/


}