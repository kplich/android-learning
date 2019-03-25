package com.example.bmi.logic

class BMIFromKgCm(val mass: Int = 0, val height: Int = 0): BMI {
    override fun getCategory(): BMICategory {
        return when {
            countBMI() < 18.5 -> BMICategory.UNDERWEIGHT
            countBMI() < 25 -> BMICategory.NORMAL
            countBMI() < 30 -> BMICategory.OVERWEIGHT
            else -> BMICategory.OBESE
        }
    }

    override fun countBMI(): Double {
        //TODO: are those lambdas ok?
        require(mass >= 0, fun(): String { return "Mass must be non-negative" })
        require(height > 0, fun(): String { return "Height must be greater than 0" })

        return mass * 10000.0 / (height * height)
    }
}