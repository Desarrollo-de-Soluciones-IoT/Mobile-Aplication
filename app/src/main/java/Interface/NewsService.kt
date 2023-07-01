package Interface
import Beans.News
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("news")
    fun getNews(): Call<List<News>>
}