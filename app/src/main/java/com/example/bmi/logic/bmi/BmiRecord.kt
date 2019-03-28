package com.example.bmi.logic.bmi

class BmiRecord(val result: String, val description: String, val date: String, val pictureId: Int) {
    companion object {
        fun fromString(other: String): BmiRecord {
            val attributes = other.split(",")

            return BmiRecord(attributes[0], attributes[1], attributes[2], attributes[3].toInt())
        }
    }

    override fun toString(): String {
        return "$result,$description,$date,$pictureId"
    }
}