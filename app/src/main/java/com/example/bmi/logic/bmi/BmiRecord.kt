package com.example.bmi.logic.bmi

//TODO: this is only temporary, no default values here!
class BmiRecord(val result: String, val description: String, val date: String, val pictureId: Int, val color: Int) {
    companion object {
        fun fromString(other: String): BmiRecord {
            val attributes = other.split(",")

            return BmiRecord(attributes[0], attributes[1], attributes[2], attributes[3].toInt(), attributes[4].toInt())
        }
    }

    override fun toString(): String {
        return "$result,$description,$date,$pictureId,$color"
    }
}