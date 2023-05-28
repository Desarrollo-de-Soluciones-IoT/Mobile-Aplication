package Beans

class Reviews {
    var id: Int
    var description: String
    var doctor_id: Int
    var patient_id: Int
    var rating: Int

    constructor(
        id: Int,
        description: String,
        doctor_id: Int,
        patient_id:Int,
        rating: Int
    ){
        this.id= id
        this.description= description
        this.doctor_id=doctor_id
        this.patient_id=patient_id
        this.rating=rating
    }
}