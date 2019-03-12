package com.example.bmi.logic

class BMIFromKgCm(var mass: Int = 0, var height: Int = 0): BMI {
    override fun getCategory(): BMICategory {
        return when {
            countBMI() < 18.5 -> BMICategory.UNDERWEIGHT
            countBMI() < 25 -> BMICategory.NORMAL
            countBMI() < 30 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }

    override fun countBMI(): Double {
        return mass * 10000.0 / (height * height)
    }
}