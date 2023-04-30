package com.example.docseeker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfilePatients: AppCompatActivity() {

    private lateinit var infoProfileComponent: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patients)

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        infoProfileComponent = findViewById(R.id.infoProfile)

        val photo = infoProfileComponent.findViewById<ImageView>(R.id.profilePicture)
        val name = infoProfileComponent.findViewById<TextView>(R.id.profileName)
        val birth = infoProfileComponent.findViewById<TextView>(R.id.profileBirth)
        val number = infoProfileComponent.findViewById<TextView>(R.id.profileNumber)
        val editButton = infoProfileComponent.findViewById<ImageButton>(R.id.editButton)

        photo.setImageResource(R.drawable.paciente_profile)
        name.text = sharedPref.getString("name", "Paciente Usuario")
        birth.text = sharedPref.getString("birth_date", "XX/XX/XXXX")
        number.text = sharedPref.getString("phone_number", "999999999")
        editButton.setOnClickListener{ v ->
            val intent = Intent(this, EditProfile::class.java)
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
    }
    fun goToEditProfile(view: View) {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }
}