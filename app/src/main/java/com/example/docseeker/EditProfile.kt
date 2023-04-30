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

        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        infoProfileComponent = findViewById(R.id.infoProfile)

        val photo = infoProfileComponent.findViewById<ImageView>(R.id.profilePicture)
        val name = infoProfileComponent.findViewById<TextView>(R.id.profileName)
        val birth = infoProfileComponent.findViewById<TextView>(R.id.profileBirth)
        val number = infoProfileComponent.findViewById<TextView>(R.id.profileNumber)

        heightInput = findViewById(R.id.heightInput)
        weightInput = findViewById(R.id.weightInput)
        birthdayInput = findViewById(R.id.birthdayInput)
        numberphoneInput = findViewById(R.id.numberphoneInput)


        photo.setImageResource(R.drawable.paciente_profile)
        name.text = sharedPref.getString("name", "Paciente Usuario")
        birth.text = sharedPref.getString("birth_date", "XX/XX/XXXX")
        number.text = sharedPref.getString("phone_number", "999999999")
    }
    fun goToProfile(view: View) {
        val sharedPref = getSharedPreferences("userLogged", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        if(!heightInput.text.isBlank()){
            editor.putString("height", heightInput.text.toString())
        }
        if(!weightInput.text.isBlank()) {
            editor.putString("weight", weightInput.text.toString())
        }
        if(!birthdayInput.text.isBlank()) {
            editor.putString("birth_date", birthdayInput.text.toString())
        }
        if(!numberphoneInput.text.isBlank()){
            editor.putString("phone_number", numberphoneInput.text.toString())
        }
        editor.apply()

        val intent = Intent(this, ProfilePatients::class.java)
        startActivity(intent)
    }

}