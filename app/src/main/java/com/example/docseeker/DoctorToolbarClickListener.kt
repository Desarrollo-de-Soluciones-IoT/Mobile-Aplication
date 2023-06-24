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
                val intent = Intent(context, NewsDoctors::class.java)
                context.startActivity(intent)
            }
            R.id.button3Doctor -> {
                val intent = Intent(context, DoctorProfile::class.java)
                context.startActivity(intent)

            }
            R.id.button4 -> {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }

        }
    }
}