package com.example.docseeker

import Beans.Appointment
import Beans.Doctors
import Beans.News
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AppointmentAdapter(var appointments: Array<Appointment>) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateView: TextView = itemView.findViewById(R.id.appointmentCardDate)
        val startView: TextView = itemView.findViewById(R.id.appointmentCardStart)
        val endView: TextView = itemView.findViewById(R.id.appointmentCardEnd)
        val doctorCardView: CardView = itemView.findViewById(R.id.appointmentCard)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_card, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.dateView.text = "Tiene una cita el: " + appointment.date
        holder.startView.text = "Hora de inicio: " + appointment.startTime
        holder.endView.text = "Hora de fin: " + appointment.endTime

    }

    fun updateAppointments(newAppointmentsList: Array<Appointment>) {
        appointments = newAppointmentsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return appointments.size
    }
}
