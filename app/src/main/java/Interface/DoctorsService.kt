package Interface

import Beans.Doctors
import Beans.News
import Beans.Patients
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DoctorsService {
    @GET("doctors")
    fun getDoctors(): Call<List<Doctors>>
    @GET("doctors/{id}")
    fun getDoctorById(@Path("id") id: Int): Call<Doctors>
    @POST("doctors")
    fun createDoctor(@Body doctor: Doctors): Call<Void>
}