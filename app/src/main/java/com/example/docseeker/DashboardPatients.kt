package com.example.docseeker

import Beans.News
import Interface.NewsService
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.docseeker.BaseUrl

class DashboardPatients : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_patients)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        //SETTING JUST THE LAST 5 NEWS TO THE CAROUSEL ADAPTER
        GlobalScope.launch(Dispatchers.Main) {
            val arrayOfNews = getNews()
            val lastFiveNews = arrayOfNews.takeLast(5).toTypedArray()
            viewPager.adapter = CarouselRVAdapter(lastFiveNews)
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        viewPager.setPageTransformer(compositePageTransformer)

        val doctorbutton = findViewById<CardView>(R.id.menu_doctor)
        doctorbutton.setOnClickListener{
            val intent = Intent(this, ListDoctors::class.java)
            startActivity(intent)
        }
        val medicalbutton = findViewById<CardView>(R.id.menu_medical_history)
        medicalbutton.setOnClickListener{
            val intent = Intent(this, MedicalHistoryPatient::class.java)
            startActivity(intent)
        }
        val servicesbutton = findViewById<CardView>(R.id.menu_services)
        servicesbutton.setOnClickListener{
            val intent = Intent(this, ListServices::class.java)
            startActivity(intent)
        }
        val appointmentsbutton = findViewById<CardView>(R.id.menu_appointments)
        appointmentsbutton.setOnClickListener{
            val intent = Intent(this, MyAppointments::class.java)
            startActivity(intent)
        }

        // FUNCTIONS TO EVERY ACTIVITY WHICH USES TOOLBAR
        val toolbarClickListener = ToolbarClickListener(this)

        // REFERENCES TO BUTTONS FROM TOOLBAR
        val button1 = findViewById<ImageButton>(R.id.button1)
        val button2 = findViewById<ImageButton>(R.id.button2)
        val button3 = findViewById<ImageButton>(R.id.button3)
        val button4 = findViewById<ImageButton>(R.id.button4)


        // SET OnClickListener WITH ToolbarClickListener
        button1.setOnClickListener(toolbarClickListener)
        button2.setOnClickListener(toolbarClickListener)
        button3.setOnClickListener(toolbarClickListener)
        button4.setOnClickListener(toolbarClickListener)

    }

    suspend fun getNews(): Array<News>{

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