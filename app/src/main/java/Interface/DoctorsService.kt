package Interface

import Beans.Doctors
import Beans.News
import retrofit2.Call
import retrofit2.http.GET

interface DoctorsService {
    @GET("doctors")
    fun getDoctors(): Call<List<Doctors>>
}