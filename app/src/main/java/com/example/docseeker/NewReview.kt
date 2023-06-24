package com.example.docseeker

import Beans.*
import Interface.AppointmentsService
import Interface.DoctorsService
import Interface.ReviewsService
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.docseeker.BaseUrl

class NewReview : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        val patientName = findViewById<TextView>(R.id.newReviewPatientName)
        val editText = findViewById<EditText>(R.id.newReviewDescription)
        var editTextContent: String = ""
        val doctorReviewName = findViewById<TextView>(R.id.doctorReviewName)
        val doctorReviewArea = findViewById<TextView>(R.id.doctorReviewArea)
        val barStar = findViewById<RatingBar>(R.id.newReviewStars)
        val btnPublish = findViewById<Button>(R.id.btnPublish)

        patientName.text = sharedPref.getString("name", "Paciente Usuario")
        doctorReviewName.text = intent.getStringExtra("doctorName")
        doctorReviewArea.text = intent.getStringExtra("doctorArea")
        btnPublish.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {

                /*var currentPatient = Patients(
                    id = (sharedPref.getString("id", "Paciente Usuario")).toString().toInt(),
                    age = Int,
                    dni = String,
                    email = String,
                    name = String,
                    password = String,
                    bmi = Float,
                    height = Int,
                    weight = Int,
                    birthDate = String,
                    phoneNumber = String,
                    userType = String
                )*/
                editTextContent = editText.text.toString()
                val doctorId = intent.getStringExtra("doctorId")
                val patientId = sharedPref.getString("id", "Paciente ID")
                var review = CreateReview(
                    id = 5,
                    description = editTextContent,
                    rating = barStar.rating.toInt(),
                    doctorId = doctorId.toString().toInt(),
                    patientId = patientId.toString().toInt()
                )

                createReview(review)

                val intent = Intent(this@NewReview, ReviewsPatient::class.java)
                intent.putExtra("doctorId", doctorId)
                intent.putExtra("doctorName", doctorReviewName.text)
                intent.putExtra("doctorArea", doctorReviewArea.text)
                startActivity(intent)
            }

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

    suspend fun getDoctorById(id: Int): Doctors {

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
            val response = doctorsService.getDoctorById(id).execute()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("No se pudo obtener el doctor")
            } else {
                throw Exception("Error al obtener el doctor")
            }
        }
    }

    fun createReview(review: CreateReview) {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val reviewService = retrofit.create(ReviewsService::class.java)
        val call = reviewService.createReview(review)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("REVIEW", "CREADO REVIEW CON EXITO")
                } else {
                    // Ocurrió un error al crear la cita
                    Log.d("REVIEW", "ALGO SALIÓ MAL")
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

    suspend fun getReviews(): Array<Reviews> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(ReviewsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = newsService.getReviews().execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}