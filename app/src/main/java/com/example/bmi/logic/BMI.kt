package com.example.bmi.logic

interface BMI {
    fun countBMI(): Double

    fun getCategory(): BMICategory
}