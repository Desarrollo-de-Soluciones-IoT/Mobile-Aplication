package Interface
import Beans.Appointment
import Beans.News
import Beans.Patients
import Beans.Prescriptions
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrescriptionsService {
    @GET("prescriptions")
    fun getPrescriptions(): Call<List<Prescriptions>>
    @POST("prescriptions")
    fun createPrescriptions(@Body prescription: Prescriptions): Call<Void>
    @GET("prescriptions/patient/{patientId}")
    fun getPrescriptionsByPatientId(@Path("patientId") patientId: Int): Call<List<Prescriptions>>
    @GET("prescriptions/doctor/{doctorId}")
    fun getPrescriptionsByDoctorId(@Path("doctorId") doctorId: Int): Call<List<Prescriptions>>

}