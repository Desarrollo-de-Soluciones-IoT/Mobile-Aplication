package Interface

import Beans.Appointment
import Beans.CreateReview
import Beans.Reviews
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewsService {
    @GET("reviews")
    fun getReviews(): Call<List<Reviews>>
    @POST("reviews")
    fun createReview(@Body review: CreateReview): Call<Void>
    @GET("reviews/doctor/{id}")
    fun getReviewsByDoctorId(@Path("id") id: Int): Call<List<Reviews>>
}