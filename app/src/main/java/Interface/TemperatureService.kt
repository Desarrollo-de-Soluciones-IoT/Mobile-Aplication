package Interface

import Beans.CreateReview
import Beans.Doctors
import Beans.Patients
import Beans.Temperature
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TemperatureService {
    @GET("temperature")
    fun getTemperatures():Call<List<Temperature>>
    @POST("temperature")
    fun createTemperature(@Body temperature: Temperature): Call<Void>
}
