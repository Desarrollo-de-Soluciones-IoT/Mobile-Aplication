package Interface

import Beans.Doctors
import Beans.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DoctorsService {
    @GET("doctors")
    fun getDoctors(): Call<List<Doctors>>
    @GET("doctors/{id}")
    fun getDoctorById(@Path("id") id: Int): Call<Doctors>
}