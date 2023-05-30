package Interface

import Beans.Reviews
import retrofit2.Call
import retrofit2.http.GET

interface ReviewsService {
    @GET("reviews")
    fun getReviews(): Call<List<Reviews>>
}