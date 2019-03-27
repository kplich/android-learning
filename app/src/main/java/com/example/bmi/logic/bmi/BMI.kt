package com.example.bmi.logic.bmi

abstract class BMI (var mass: Int, var height: Int) {
    abstract fun countBMI(): Double
}