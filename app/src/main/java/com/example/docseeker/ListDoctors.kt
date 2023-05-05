package com.example.docseeker

import Beans.Doctors
import Beans.News
import Interface.DoctorsService
import Interface.NewsService
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

class ListDoctors : AppCompatActivity() {
    private lateinit var doctorsAdapter: DoctorsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_doctors)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerDoctors)
        recyclerView.layoutManager = LinearLayoutManager(this)
        doctorsAdapter = DoctorsAdapter(emptyArray())
        recyclerView.adapter = doctorsAdapter

        //SETTING ALL DOCTORS
        GlobalScope.launch(Dispatchers.Main) {
            val arrayOfDoctors = getDoctors()
            doctorsAdapter.updateDoctors(arrayOfDoctors)

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



    suspend fun getDoctors(): Array<Doctors> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            //.baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
            //CONNECT TO LOCALHOST
            .baseUrl("http://192.168.1.180:8080/api/v1/")
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