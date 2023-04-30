package Beans

import java.util.Date

class Patients {
    var age: Int
    var dni: String
    var email: String
    var name: String
    var password: String
    var height: Int
    var weight: Int
    var birthDate: String
    var phoneNumber: String
    var userType:String

    constructor(
        age: Int,
        dni: String,
        email: String,
        name: String,
        password: String,
        height: Int,
        weight: Int,
        birthDate: String,
        phoneNumber: String,
        userType: String
    ) {
        this.age = age
        this.dni = dni
        this.email = email
        this.name = name
        this.password = password
        this.height = height
        this.weight = weight
        this.birthDate = birthDate
        this.phoneNumber = phoneNumber
        this.userType = userType
    }



}