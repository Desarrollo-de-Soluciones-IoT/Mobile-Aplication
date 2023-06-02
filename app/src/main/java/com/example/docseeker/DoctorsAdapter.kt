package com.example.docseeker

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

class DoctorsAdapter(var doctors: Array<Doctors>) : RecyclerView.Adapter<DoctorsAdapter.DoctorsViewHolder>() {
    class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView: TextView = itemView.findViewById(R.id.doctorCardName)
        val areaView: TextView = itemView.findViewById(R.id.doctorCardArea)
        val priceView: TextView = itemView.findViewById(R.id.doctorCardPrice)
        val imageView: ImageView = itemView.findViewById(R.id.doctorCardImage)
        val doctorCardView: CardView = itemView.findViewById(R.id.doctorCard)
        val doctorBookView: Button = itemView.findViewById(R.id.dateDoctor)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_card, parent, false)
        return DoctorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.nameView.text = doctor.name
        holder.areaView.text = doctor.speciality
        holder.priceView.text = doctor.doctorFee.toString()
        holder.areaView.text = doctor.speciality
        holder.imageView.setImageResource(R.drawable.user_type_health_professional)

        /*Glide.with(holder.itemView)
            .load(doctor.imageUrl)
            .into(holder.imageView)*/

        //GO TO DOCTOR PROFILE
        holder.doctorCardView.setOnClickListener {
            Log.d("DOCTORAdapter", doctor.id.toString())
            val intent = Intent(holder.itemView.context, DoctorProfilePatient::class.java)
            intent.putExtra("doctorId", doctor.id.toString())
            intent.putExtra("doctorName", doctor.name)
            intent.putExtra("doctorAge", doctor.age.toString())
            intent.putExtra("doctorPrice", doctor.doctorFee.toString())
            intent.putExtra("doctorYears", doctor.experienceYears.toString())
            intent.putExtra("doctorArea", doctor.speciality)
            intent.putExtra("doctorDescription", doctor.description)
            intent.putExtra("doctorNPatients", doctor.patientsAssisted.toString())
            holder.itemView.context.startActivity(intent)
        }

        //GO TO DOCTOR BOOK APPOINTMENT
        holder.doctorBookView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookAppointment::class.java)
            intent.putExtra("doctorId", doctor.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateDoctors(newDoctorsList: Array<Doctors>) {
        doctors = newDoctorsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return doctors.size
    }
}