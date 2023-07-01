package com.example.docseeker

import Beans.News
import Beans.Prescriptions
import Interface.NewsService
import Interface.PrescriptionsService
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TableRow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListPrescriptions : AppCompatActivity() {
    private lateinit var prescriptionAdapter: PrescriptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_medical_prescription)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerPrescription)
        recyclerView.layoutManager = LinearLayoutManager(this)
        prescriptionAdapter = PrescriptionsAdapter(emptyArray())
        recyclerView.adapter = prescriptionAdapter

        //SETTING ALL NEWS
        GlobalScope.launch(Dispatchers.Main) {
            val patientId = (sharedPref.getString("id", "PatientID")).toString().toInt()
            val arrayOfPrescriptions = getPrescriptionsByPatientId(patientId)
            prescriptionAdapter.updatePrescriptionsList(arrayOfPrescriptions)

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
        button4.setOnClickListener(toolbarClickListener)

    }

    suspend fun getPrescriptionsByPatientId(patientId: Int): Array<Prescriptions> {

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
            val response = prescriptionsService.getPrescriptionsByPatientId(patientId).execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}