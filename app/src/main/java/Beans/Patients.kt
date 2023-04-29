package Beans

class Patients {
    var age: Int
    var DNI: String
    var email: String
    var name: String
    var password: String
    var user_type:Boolean

    constructor(
        age: Int,
        DNI: String,
        email: String,
        name: String,
        password: String,
        user_type: Boolean
    ) {
        this.age = age
        this.DNI = DNI
        this.email = email
        this.name = name
        this.password = password
        this.user_type = user_type
    }



}