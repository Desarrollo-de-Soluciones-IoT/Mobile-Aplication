package Beans

class CreateReview {
    var id: Int?
    var description: String
    var rating: Int
    var doctorId: Int
    var patientId: Int

    constructor(
        id: Int?,
        description: String,
        rating: Int,
        doctorId: Int,
        patientId:Int,
    ){
        this.id= id
        this.description= description
        this.rating=rating
        this.doctorId=doctorId
        this.patientId=patientId
    }
}