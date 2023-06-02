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

class DashboardDoctors : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_doctors)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager2)

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

        val doctorbutton = findViewById<CardView>(R.id.menu_prescriptions_doctor)
        doctorbutton.setOnClickListener{
            val intent = Intent(this, ListDoctors::class.java)
            startActivity(intent)
        }
        val medicalbutton = findViewById<CardView>(R.id.menu_agend)
        medicalbutton.setOnClickListener{
            val intent = Intent(this, MedicalHistoryPatient::class.java)
            startActivity(intent)
        }
        val prescriptionsbutton = findViewById<CardView>(R.id.menu_patients)
        prescriptionsbutton.setOnClickListener{
            val intent = Intent(this, ListPrescriptions::class.java)
            startActivity(intent)
        }
        val appointmentsbutton = findViewById<CardView>(R.id.menu_finance)
        appointmentsbutton.setOnClickListener{
            val intent = Intent(this, MyAppointments::class.java)
            startActivity(intent)
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

    suspend fun getNews(): Array<News>{

        //GETTING NEWS DATA FROM ENDPOINT
        val retrofit = Retrofit.Builder()
            //CONNECT TO DEPLOYED API
            .baseUrl("https://spring-docseeker-dockseeker-be.azuremicroservices.io/api/v1/")
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