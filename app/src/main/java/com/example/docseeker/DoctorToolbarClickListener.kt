package com.example.docseeker

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity


class DoctorToolbarClickListener(private val context: Context):View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1Doctor -> {
                val intent = Intent(context, DashboardDoctors::class.java)
                context.startActivity(intent)
            }
            R.id.button2Doctor -> {
                val intent = Intent(context, NewsPatients::class.java)
                context.startActivity(intent)
            }
            R.id.button3Doctor -> {
                val intent = Intent(context, ProfilePatients::class.java)
                context.startActivity(intent)

            }
            R.id.button4 -> {

            }

        }
    }
}