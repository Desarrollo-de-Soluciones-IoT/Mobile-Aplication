package Interface

import Beans.Appointment
import Beans.Doctors
import Beans.News
import Beans.Reviews
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
}