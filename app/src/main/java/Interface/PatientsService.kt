package Interface

import Beans.Patients
import retrofit2.Call
import retrofit2.http.GET

interface PatientsService {
    @GET("patients")
    fun getPatients():Call<List<Patients>>
}
