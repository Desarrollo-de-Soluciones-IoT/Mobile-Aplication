package Beans

import java.util.Date

class Pulse {
    var id: Int
    var value: Float
    var timestamp: Date

    constructor(
        id: Int,
        value: Float,
        timestamp: Date,

    ) {
        this.id = id
        this.value = value
        this.timestamp = timestamp

    }



}