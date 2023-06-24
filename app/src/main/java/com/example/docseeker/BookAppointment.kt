package com.example.docseeker

import Beans.Appointment
import Beans.Doctors
import Interface.AppointmentsService
import Interface.DoctorsService
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.format.DateTimeFormatter
import java.util.*
import com.example.docseeker.BaseUrl

class BookAppointment : AppCompatActivity() {
    private var hora1: String = ""
    private var hora2: String = ""

    private lateinit var editTextHora1: EditText
    private lateinit var editTextHora2: EditText
    private lateinit var datePicker: DatePicker

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)

        datePicker = findViewById(R.id.dateDoctorAppointment)
        editTextHora1 = findViewById(R.id.editTextHora1)
        editTextHora2 = findViewById(R.id.editTextHora2)
        val doctorId = intent.getStringExtra("doctorId")

        var btnAppointment = findViewById<Button>(R.id.appointmentButton)

        editTextHora1.setOnClickListener {
            showTimePickerDialog { hourOfDay, minute ->
                // Formatear la hora seleccionada
                hora1 = String.format("%02d:%02d", hourOfDay, minute)
                // Actualizar el texto del EditText con la hora seleccionada
                editTextHora1.setText(hora1)
            }
        }

        editTextHora2.setOnClickListener {
            showTimePickerDialog { hourOfDay, minute ->
                // Formatear la hora seleccionada
                hora2 = String.format("%02d:%02d", hourOfDay, minute)
                // Actualizar el texto del EditText con la hora seleccionada
                editTextHora2.setText(hora2)
            }
        }

        btnAppointment.setOnClickListener{
            val year: Int = datePicker.year
            val month: Int = datePicker.month + 1
            val dayOfMonth: Int = datePicker.dayOfMonth
            val formattedDate: String = String.format("%d-%02d-%02d", year, month, dayOfMonth)

            var appointment = Appointment(
                id = 5,
                date = formattedDate,
                startTime = hora1,
                endTime = hora2,
                doctorId = doctorId.toString().toInt(),
                patientId = sharedPref.getString("id", "Paciente Usuario").toString().toInt()
            )
            createAppointments(appointment)
            val intent = Intent(this, DashboardPatients::class.java)
            startActivity(intent)
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

    fun createAppointments(appointment: Appointment) {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val appointmentService = retrofit.create(AppointmentsService::class.java)


        val call = appointmentService.createAppointment(appointment)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("APPOINTMENT", "CREADO CITA CON EXITO")
                } else {
                    // Ocurrió un error al crear la cita
                    Log.d("APPOINTMENT", "ALGO SALIÓ MAL")
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

    private fun showTimePickerDialog(listener: (hourOfDay: Int, minute: Int) -> Unit) {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute -> listener(hourOfDay, minute) },
            hour,
            minute,
            DateFormat.is24HourFormat(this)
        )

        timePickerDialog.show()
    }
}