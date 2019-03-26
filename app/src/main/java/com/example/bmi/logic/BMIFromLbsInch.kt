package com.example.bmi.logic

class BMIFromLbsInch(val mass: Int = 0, val height: Int = 0): BMI {
    override fun countBMI(): Double {
        require(mass >= 0, fun(): String { return "Mass must be non-negative" })
        require(height > 0, fun(): String { return "Height must be greater than 0" })

        return (703 * mass / (height * height)).toDouble()
    }
}