package com.example.bmi.logic.bmi

class BMIFromLbsInch(mass: Int, height: Int): BMI(mass, height) {
    override fun countBMI(): Double {
        require(mass >= 0, fun(): String { return "Mass must be non-negative" })
        require(height > 0, fun(): String { return "Height must be greater than 0" })

        return (703 * mass / (height * height)).toDouble()
    }
}