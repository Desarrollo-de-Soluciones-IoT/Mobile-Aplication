package Beans

class Doctors {
    var id: Int
    var age: Int
    var dni: String
    var email: String
    var name: String
    var password: String
    var userType: String
    var description: String
    var doctor_fee: Int
    var experience_years: Int
    var patients_assisted: Int
    var profile_photo: String
    var speciality: String

    constructor(
        id: Int,
        age: Int,
        dni: String,
        email: String,
        name: String,
        password: String,
        userType: String,
        description: String,
        doctor_fee: Int,
        experience_years: Int,
        patients_assisted: Int,
        profile_photo: String,
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
        this.doctor_fee = doctor_fee
        this.experience_years = experience_years
        this.patients_assisted = patients_assisted
        this.profile_photo = profile_photo
        this.speciality = speciality
    }
}