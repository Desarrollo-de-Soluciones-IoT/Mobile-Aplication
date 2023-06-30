package Beans

class Reviews {
    var id: Int?
    var description: String
    var doctorId: Int
    var patientName: String
    var rating: Int

    constructor(
        id: Int?,
        description: String,
        doctorId: Int,
        patientName:String,
        rating: Int
    ){
        this.id= id
        this.description= description
        this.doctorId=doctorId
        this.patientName=patientName
        this.rating=rating
    }
}