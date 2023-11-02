package com.example.docseeker

import Beans.Doctors
import Beans.Patients
import Interface.DoctorsService
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
import com.example.docseeker.BaseUrl

class Doctors_LogIn : AppCompatActivity() {
    lateinit var userLogged: Doctors
    //var arrayOfPatients: Array<Patients>? = null // Inicializar la variable como null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_log_in)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            builder.detectFileUriExposure()
        }

        // SharedPreferences USER LOGGED
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val txtEmail: EditText =findViewById(R.id.emailInputDoctor)
        val txtPassword: EditText =findViewById(R.id.passwordInputDoctor)
        val txtComment: TextView =findViewById(R.id.txtCommentDoctor)
        val btnLogIn: Button =findViewById(R.id.logInButtonDoctor)


        //GETTING PATIENTS DATA FROM ENDPOINT
        GlobalScope.launch(Dispatchers.Main) {
            val arrayOfDoctors = getDoctors()

            btnLogIn.setOnClickListener(){
                val user:String=txtEmail.text.toString()
                val password:String=txtPassword.text.toString()
                for (doctor in arrayOfDoctors) {
                    if (doctor.email == user && doctor.password == password) {
                        userLogged = doctor
                        // SAVE SharedPreferences
                        editor.putString("id", userLogged.id.toString())
                        editor.putString("age", userLogged.age.toString())
                        editor.putString("DNI", userLogged.dni)
                        editor.putString("email", userLogged.email)
                        editor.putString("name", userLogged.name)
                        editor.putString("password", userLogged.password)
                        editor.putString("description", userLogged.description)
                        editor.putString("doctor_fee", userLogged.doctorFee.toString())
                        editor.putString("patients_assisted", userLogged.patientsAssisted.toString())
                        editor.putString("experience_years", userLogged.experienceYears.toString())
                        editor.putString("speciality", userLogged.speciality)
                        editor.putString("userType", userLogged.userType)

                        editor.apply()

                        val intent= Intent(this@Doctors_LogIn, DashboardDoctors::class.java)
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

    suspend fun getDoctors(): Array<Doctors> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(DoctorsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = newsService.getDoctors().execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }


}