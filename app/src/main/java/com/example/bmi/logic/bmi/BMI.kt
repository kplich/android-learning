package com.example.bmi.logic.bmi

class BMI (var mass: Int, var height: Int) {
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

    fun countBMI(imperialUnits: Boolean): Double = if(imperialUnits) fromLbsInch() else fromKgCm()
}