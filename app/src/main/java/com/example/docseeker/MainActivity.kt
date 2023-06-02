package com.example.docseeker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToUserSelectionRegister(view: View) {
        val intent = Intent(this, UserSelectionRegister::class.java)
        startActivity(intent)
    }

    fun goToUserSelection(view: View) {
        val intent = Intent(this, UserSelection::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }
}
