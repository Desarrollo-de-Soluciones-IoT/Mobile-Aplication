package Interface

import Beans.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentsService {
    @GET("appointments")
    fun getAppointments(): Call<List<Appointment>>
    @POST("appointments")
    fun createAppointment(@Body appointment: Appointment): Call<Void>
    @GET("appointments/patient/{patientId}")
    fun getAppointmentsByPatientId(@Path("patientId") patientId: Int): Call<List<Appointment>>
    @GET("appointments/doctor/{doctorId}")
    fun getAppointmentsByDoctorId(@Path("doctorId") doctorId: Int): Call<List<Appointment>>
    @GET("appointments/doctor/{doctorId}/date/{date}")
    fun getAppointmentsByDoctorIdAndDate(@Path("doctorId") doctorId: Int, @Path("date") date: String): Call<List<Appointment>>
    @GET("appointments/doctor/{doctorId}/patients")
    fun getPatientsByDoctorId(@Path("doctorId") doctorId: Int): Call<List<Patients>>
}