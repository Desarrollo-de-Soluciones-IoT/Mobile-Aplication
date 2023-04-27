package com.example.docseeker

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity


class ToolbarClickListener(private val context: Context):View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> {
                val intent = Intent(context, DashboardPatients::class.java)
                context.startActivity(intent)
            }
            R.id.button2 -> {

            }
            R.id.button3 -> {

            }
            R.id.button4 -> {

            }

        }
    }
}