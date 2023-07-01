package com.example.docseeker

import Beans.Appointment
import Beans.Patients
import Beans.Prescriptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PrescriptionsAdapter(var prescriptionsList: Array<Prescriptions>) : RecyclerView.Adapter<PrescriptionsAdapter.PrescriptionsViewHolder>() {
    class PrescriptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateView: TextView = itemView.findViewById(R.id.prescriptionCardDate)
        val medicineView: TextView = itemView.findViewById(R.id.prescriptionCardMedicine)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prescription_item, parent, false)
        return PrescriptionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionsViewHolder, position: Int) {
        val prescription = prescriptionsList[position]
        holder.dateView.text = prescription.date
        holder.medicineView.text = prescription.medicineName

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PrescriptionPatient::class.java)
            intent.putExtra("doctorId", prescription.doctorId.toString())
            intent.putExtra("medicineName", prescription.medicineName)
            intent.putExtra("medicineDosage", prescription.medicineDosage)
            intent.putExtra("medicineDuration", prescription.medicineDuration)
            intent.putExtra("date", prescription.date)
            holder.itemView.context.startActivity(intent)
        }

    }

    fun updatePrescriptionsList(newPrescriptionsList: Array<Prescriptions>) {
        prescriptionsList = newPrescriptionsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return prescriptionsList.size
    }
}