package com.example.bmi.logic

interface BMI {
    fun countBMI(): Double

    fun getCategory(): BMICategory {
        return when {
            countBMI() < 18.5 -> BMICategory.UNDERWEIGHT
            countBMI() < 25 -> BMICategory.NORMAL
            countBMI() < 30 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }
}