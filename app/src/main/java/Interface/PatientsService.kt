package Interface

import Beans.CreateReview
import Beans.Doctors
import Beans.Patients
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PatientsService {
    @GET("patients")
    fun getPatients():Call<List<Patients>>
    @GET("patients/{id}")
    fun getPatientsById(@Path("id") id: Int): Call<Patients>
    @POST("patients")
    fun createPatient(@Body patient: Patients): Call<Void>
}
