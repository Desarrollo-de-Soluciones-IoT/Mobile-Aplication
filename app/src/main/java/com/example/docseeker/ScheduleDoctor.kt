package com.example.docseeker

import Beans.Appointment
import Interface.AppointmentsService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ScheduleDoctor : AppCompatActivity() {
    private lateinit var appointmentsAdapter : AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_doctor)

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerAppointmentsDoctor)
        recyclerView.layoutManager= LinearLayoutManager(this)
        appointmentsAdapter = AppointmentAdapter(emptyArray())
        recyclerView.adapter = appointmentsAdapter

        val datePicker = findViewById<DatePicker>(R.id.dateAppointment)
        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth,
            DatePicker.OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                val formattedDate = obtenerFechaEnFormato(year, monthOfYear, dayOfMonth)
                // Llama a tu función con la fecha seleccionada
                Log.d("TAG", "Fecha seleccionada: $formattedDate")

                GlobalScope.launch(Dispatchers.Main){
                    val doctorId = (sharedPref.getString("id", "DoctorID")).toString().toInt()
                    val arrayOfAppointments = getAppointmentsByDoctorIdAndDate(doctorId, formattedDate)
                    //val arrayOfAppointments = getAppointments()

                    appointmentsAdapter.updateAppointments(arrayOfAppointments)
                }
            })

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
    }

    fun obtenerFechaEnFormato(year: Int, month: Int, day: Int): String {
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fecha = Calendar.getInstance()
        fecha.set(year, month, day)
        return formatoFecha.format(fecha.time)
    }


    suspend fun getAppointmentsByDoctorIdAndDate(doctorId: Int, date: String): Array<Appointment> {
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(AppointmentsService::class.java)
        return withContext(Dispatchers.IO) {
            val response = newsService.getAppointmentsByDoctorIdAndDate(doctorId, date).execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}