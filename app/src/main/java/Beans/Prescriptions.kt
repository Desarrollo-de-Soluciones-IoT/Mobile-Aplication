package Beans

import java.util.Date

class Prescriptions {
    var id: Int?
    var patientId: Int
    var doctorId: Int
    var medicineName: String
    var medicineDosage: String
    var medicineDuration: String
    var date: String


    constructor(
        id: Int?,
        patientId: Int,
        doctorId: Int,
        medicineName: String,
        medicineDosage: String,
        medicineDuration: String,
        date: String
    ) {
        this.id = id
        this.patientId = patientId
        this.doctorId = doctorId
        this.medicineName = medicineName
        this.medicineDosage = medicineDosage
        this.medicineDuration = medicineDuration
        this.date = date
    }



}