package com.example.bmi.logic.bmi

abstract class BMI (var mass: Int, var height: Int) {
    abstract fun countBMI(): Double

    fun getCategory(): BMICategory {
        return when {
            mass == 0 || height == 0 -> BMICategory.NA
            countBMI() < 18.5 -> BMICategory.UNDERWEIGHT
            countBMI() < 25 -> BMICategory.NORMAL
            countBMI() < 30 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }
}