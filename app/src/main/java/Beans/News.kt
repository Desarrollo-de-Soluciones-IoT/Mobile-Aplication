package Beans

class News {
    var title:String
    var description:String
    var imageUrl:String

    constructor(
        title:String,
        description:String,
        imageUrl:String
    ) {
        this.title = title
        this.description = description
        this.imageUrl = imageUrl
    }
}