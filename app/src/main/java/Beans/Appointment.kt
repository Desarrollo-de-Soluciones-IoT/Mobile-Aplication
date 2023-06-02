package Beans

import java.util.Date

class Appointment {
    var id: Int
    var date: String
    var startTime: String
    var endTime: String
    var doctorId: Int
    var patientId: Int

    constructor(
        id: Int,
        date: String,
        startTime: String,
        endTime: String,
        doctorId: Int,
        patientId: Int
    ) {
        this.id = id
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
        this.doctorId = doctorId
        this.patientId = patientId
    }


}