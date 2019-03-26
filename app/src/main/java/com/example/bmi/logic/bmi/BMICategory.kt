package com.example.bmi.logic.bmi

enum class BMICategory(val shortDescription: String, val longDescription: String, val color: Int) {
    NA("", "", 0x008577),
    UNDERWEIGHT("UNDERWEIGHT", "It seems you're malnutritioned. Make sure you get enough calories and vitamins everyday!", 0x00A693),
    NORMAL("NORMAL WEIGHT", "It seems your weight is correct. Keep it up!", 0x3366CC),
    OVERWEIGHT("OVERWEIGHT", "Your BMI is a little too high. You may have to cut your calorie intake.", 0xB80000),
    OBESE("OBESITY", "You may be obese. Come up with a plan to lose some weight as soon as possible. Obesity can increase risk of death!", 0x35001C)
}