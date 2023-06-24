package com.example.docseeker

import Beans.Appointment
import Beans.Patients
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PatientsAdapter(var patientsList: Array<Patients>) : RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {
    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView: TextView = itemView.findViewById(R.id.patientCardName)
        val dniView: TextView = itemView.findViewById(R.id.patientCardDNI)
        val ageView: TextView = itemView.findViewById(R.id.patientCardAge)
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

    }

    fun updateAppointments(newPatientsList: Array<Patients>) {
        patientsList = newPatientsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return patientsList.size
    }
}