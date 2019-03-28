package com.example.bmi.logic.bmi

import android.content.res.Resources
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class BmiRecord(bmiObject: BMI, resources: Resources) {
    companion object {
        const val DATE_PATTERN = "dd.MM.yyyy"
    }

    var bmiResult: BigDecimal = BigDecimal(bmiObject.countBMI()).setScale(2, RoundingMode.HALF_UP)
    var bmiCategoryDescription: String = bmiObject.getCategory().getShortDescription(resources)
    var bmiCategory: BmiCategory = bmiObject.getCategory()
    var date: Date = Date()

    override fun toString(): String {
        val builder = StringBuilder()

        builder.append(bmiResult.toString())
        builder.append(",")
        builder.append(bmiCategoryDescription)
        builder.append(",")
        builder.append(SimpleDateFormat(DATE_PATTERN).format(date))

        return builder.toString()
    }

    fun fromString(input: String) {
        val strings = input.split(",")

        bmiResult = BigDecimal(strings[0])
        bmiCategoryDescription = strings[1]
        date = SimpleDateFormat(DATE_PATTERN).parse(strings[2])

    }
}