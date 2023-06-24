package com.example.docseeker

import androidx.appcompat.app.AppCompatActivity
import Beans.News
import Beans.Patients
import Interface.NewsService
import Interface.PatientsService
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.os.Bundle

class NewsDoctors : AppCompatActivity() {
    private lateinit var newsAdapter: NewsAdapterDoctor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_doctors)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerNewsDoctor)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapterDoctor(emptyArray())
        recyclerView.adapter = newsAdapter

        //SETTING ALL NEWS
        GlobalScope.launch(Dispatchers.Main) {
            val arrayOfNews = getNews()
            newsAdapter.updateNews(arrayOfNews)

        }

        // FUNCTIONS TO EVERY ACTIVITY WHICH USES TOOLBAR
        val toolbarClickListener = DoctorToolbarClickListener(this)

        // REFERENCES TO BUTTONS FROM TOOLBAR
        val button1 = findViewById<ImageButton>(R.id.button1Doctor)
        val button2 = findViewById<ImageButton>(R.id.button2Doctor)
        val button3 = findViewById<ImageButton>(R.id.button3Doctor)
        val button4 = findViewById<ImageButton>(R.id.button4Doctor)


        // SET OnClickListener WITH ToolbarClickListener
        button1.setOnClickListener(toolbarClickListener)
        button2.setOnClickListener(toolbarClickListener)
        button3.setOnClickListener(toolbarClickListener)
    }

    suspend fun getNews(): Array<News> {

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl(BaseUrl.base_url)
            //CONNECT TO LOCALHOST
            //.baseUrl("http://192.168.1.180:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(NewsService::class.java)

        return withContext(Dispatchers.IO) {
            val response = newsService.getNews().execute()
            if (response.isSuccessful) {
                response.body()?.toTypedArray() ?: emptyArray()
            } else {
                emptyArray()
            }
        }
    }
}