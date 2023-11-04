package Beans

class Doctors {
    var id: Int?
    var age: Int
    var dni: String
    var email: String
    var name: String
    var password: String
    var userType: Int
    var description: String
    var doctorFee: Float
    var experienceYears: Int
    var patientsAssisted: Int
    var profilePhoto: String
    var speciality: String

    constructor(
        id: Int?,
        age: Int,
        dni: String,
        email: String,
        name: String,
        password: String,
        userType: Int,
        description: String,
        doctorFee: Float,
        experienceYears: Int,
        patientsAssisted: Int,
        profilePhoto: String,
        speciality: String
    ) {
        this.id = id
        this.age = age
        this.dni = dni
        this.email = email
        this.name = name
        this.password = password
        this.userType = userType
        this.description = description
        this.doctorFee = doctorFee
        this.experienceYears = experienceYears
        this.patientsAssisted = patientsAssisted
        this.profilePhoto = profilePhoto
        this.speciality = speciality
    }
}