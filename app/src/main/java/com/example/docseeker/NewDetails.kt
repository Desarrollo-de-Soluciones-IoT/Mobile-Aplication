package com.example.docseeker

import Beans.News
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class NewDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_details)

        val titleView: TextView = findViewById(R.id.newDetailTitle)
        val descriptionView: TextView = findViewById(R.id.newDetailDescription)
        val imageView: ImageView = findViewById(R.id.newDetailImage)

        val title = intent.getStringExtra("titleNew")
        val imageUrl = intent.getStringExtra("imageUrlNew")
        val content = intent.getStringExtra("descriptionNew")

        titleView.text = title
        descriptionView.text = content
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

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
    }
}