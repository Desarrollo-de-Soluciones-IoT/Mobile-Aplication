package Beans

import java.util.Date

class Patients {
    var id: Int
    var age: Int
    var dni: String
    var email: String
    var name: String
    var password: String
    var bmi: Float?
    var height: Int?
    var weight: Int?
    var birthDate: String
    var phoneNumber: String
    var userType:String
    var reviews:List<Reviews>
    var allergies:List<String>

    constructor(
        id: Int,
        age: Int,
        dni: String,
        email: String,
        name: String,
        password: String,
        bmi: Float?,
        height: Int?,
        weight: Int?,
        birthDate: String,
        phoneNumber: String,
        userType: String,
        reviews:List<Reviews>,
        allergies:List<String>
    ) {
        this.id = id
        this.age = age
        this.dni = dni
        this.email = email
        this.name = name
        this.password = password
        this.bmi = bmi
        this.height = height
        this.weight = weight
        this.birthDate = birthDate
        this.phoneNumber = phoneNumber
        this.userType = userType
        this.reviews = reviews
        this.allergies = allergies
    }



}