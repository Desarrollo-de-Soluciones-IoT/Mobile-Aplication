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

class EditProfile : AppCompatActivity() {
    private lateinit var infoProfileComponent: View
    private lateinit var heightInput:TextView
    private lateinit var weightInput:TextView
    private lateinit var birthdayInput:TextView
    private lateinit var numberphoneInput:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_patients_edit)

        infoProfileComponent = findViewById(R.id.infoProfile)
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)


        val photo = infoProfileComponent.findViewById<ImageView>(R.id.profilePicture)
        val name = infoProfileComponent.findViewById<TextView>(R.id.profileName)
        val birth = infoProfileComponent.findViewById<TextView>(R.id.profileBirth)
        val number = infoProfileComponent.findViewById<TextView>(R.id.profileNumber)

        heightInput = infoProfileComponent.findViewById(R.id.heightInput)
        weightInput = infoProfileComponent.findViewById(R.id.weightInput)
        birthdayInput = infoProfileComponent.findViewById(R.id.birthdayInput)
        numberphoneInput = infoProfileComponent.findViewById(R.id.numberphoneInput)


        photo.setImageResource(R.drawable.paciente_profile)
        name.text = sharedPref.getString("name", "Paciente Usuario")
        birth.text = sharedPref.getString("birth_date", "XX/XX/XXXX")
        number.text = sharedPref.getString("phone_number", "999999999")
    }
    fun goToProfile(view: View) {
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putString("height", heightInput.toString())
        editor.putString("weight", weightInput.toString())
        editor.putString("birth_date", birthdayInput.toString())
        editor.putString("phone_number", numberphoneInput.toString())

        val intent = Intent(this, ProfilePatients::class.java)
        startActivity(intent)
    }
}