package com.example.docseeker

import Beans.Appointment
import Beans.Patients
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PatientsAdapter(var patientsList: Array<Patients>) : RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {
    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView: TextView = itemView.findViewById(R.id.patientCardName)
        val dniView: TextView = itemView.findViewById(R.id.patientCardDNI)
        val ageView: TextView = itemView.findViewById(R.id.patientCardAge)
        val prescriptionButtonView: Button = itemView.findViewById(R.id.prescriptionButton)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.patient_card, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patientsList[position]
        holder.nameView.text = "Nombre: " + patient.name
        holder.dniView.text = "DNI: " + patient.dni
        holder.ageView.text = "Edad: " + patient.age

        //GO TO NEW PRESCRIPTION
        holder.prescriptionButtonView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewPrescription::class.java)
            intent.putExtra("patientId", patient.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateAppointments(newPatientsList: Array<Patients>) {
        patientsList = newPatientsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return patientsList.size
    }
}