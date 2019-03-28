package com.example.bmi.logic.bmi

import android.content.res.Resources

class BMI (var mass: Int, var height: Int, var usingImperialUnits: Boolean) {
    private fun fromKgCm(): Double {
        require(mass > 0) {"Mass must be greater than 0"}
        require(height > 0) {"Height must be greater than 0"}

        return mass * 10000.0 / (height * height)
    }

    private fun fromLbsInch(): Double {
        require(mass > 0) {"Mass must be greater than 0"}
        require(height > 0) {"Height must be greater than 0"}

        return (703 * mass / (height * height)).toDouble()
    }

    fun countBMI(): Double = if(usingImperialUnits) fromLbsInch() else fromKgCm()

    fun getCategory(): BmiCategory {
        val result = countBMI()
        return when {
            result < 18.5 -> BmiCategory.UNDERWEIGHT
            result < 25 -> BmiCategory.NORMAL
            result < 30 -> BmiCategory.OVERWEIGHT
            result < 35 -> BmiCategory.OBESE
            else -> BmiCategory.NA
        }
    }

    fun getRecord(resources: Resources): BmiRecord {
        return BmiRecord(this, resources)
    }
}