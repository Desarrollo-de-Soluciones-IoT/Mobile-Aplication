package com.example.docseeker

import android.content.Context
import android.content.Intent
import android.view.View

class ProfilePatients(private val context: Context): View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> {
                val intent = Intent(context, EditProfile::class.java)
                context.startActivity(intent)
            }

        }
    }
}