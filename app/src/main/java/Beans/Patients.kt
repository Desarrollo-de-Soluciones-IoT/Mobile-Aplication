package Beans

import java.util.Date

class Patients {
    var age: Int
    var DNI: String
    var email: String
    var name: String
    var password: String
    var height: Int
    var weight: Int
    var birth_date: String
    var phone_number: Int
    var user_type:Boolean

    constructor(
        age: Int,
        DNI: String,
        email: String,
        name: String,
        password: String,
        height: Int,
        weight: Int,
        birth_date: String,
        phone_number: Int,
        user_type: Boolean
    ) {
        this.age = age
        this.DNI = DNI
        this.email = email
        this.name = name
        this.password = password
        this.height = height
        this.weight = weight
        this.birth_date = birth_date
        this.phone_number = phone_number
        this.user_type = user_type
    }



}