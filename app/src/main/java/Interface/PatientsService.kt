package Interface

import Beans.CreateReview
import Beans.Patients
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PatientsService {
    @GET("patients")
    fun getPatients():Call<List<Patients>>
    @POST("patients")
    fun createPatient(@Body patient: Patients): Call<Void>
}
