package com.example.bmi.logic.bmi

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BmiRecord(val result: String, val description: String, val date: Date, val pictureId: Int, val color: Int) {
    companion object {
        fun fromString(other: String): BmiRecord {
            val attributes = other.split(",")

            val maybeDate = try {
                EXTENDED_DATE_FORMATTER.parse(attributes[2])
            } catch (e: ParseException) {
                Date()
            }

            return BmiRecord(
                attributes[0],
                attributes[1],
                maybeDate,
                attributes[3].toInt(),
                attributes[4].toInt()
            )
        }

        private const val SIMPLE_DATE_PATTERN = "dd.MM.yyyy"
        val SIMPLE_DATE_FORMATTER: SimpleDateFormat = SimpleDateFormat(SIMPLE_DATE_PATTERN, Locale.US)

        private const val EXTENDED_DATE_PATTERN = "d MMM yyyy HH:mm:ss"
        val EXTENDED_DATE_FORMATTER = SimpleDateFormat(EXTENDED_DATE_PATTERN, Locale.US)

    }

    override fun toString(): String {
        return "$result,$description,${EXTENDED_DATE_FORMATTER.format(date)},$pictureId,$color"
    }
}