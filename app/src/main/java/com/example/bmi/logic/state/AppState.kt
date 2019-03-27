package com.example.bmi.logic.state

import android.content.res.Resources
import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.bmi.R
import com.example.bmi.logic.bmi.BMI
import com.example.bmi.logic.bmi.BMIFromKgCm
import com.example.bmi.logic.bmi.BMIFromLbsInch
import java.math.BigDecimal
import java.math.RoundingMode

class AppState {

    private lateinit var resources: Resources
    private var imperialUnits: Boolean = false
    private var bmi: BMI = BMIFromKgCm(0, 0)

    fun setMassAndHeight(mass: Int, height: Int) {
        bmi.mass = mass
        bmi.height = height
    }

    fun getBmi(): BigDecimal = BigDecimal.valueOf(bmi.countBMI()).setScale(2, RoundingMode.HALF_UP)

    fun getMassDescription(): String {
        return if(imperialUnits) {
            "Mass [lbs]"
        } else {
            "Mass [kg]"
        }
    }

    fun getHeightDescription(): String {
        return if(imperialUnits) {
            "Height [inch]"
        } else {
            "Height [cm]"
        }
    }

    fun getShortDescription(): String {
        val result = bmi.countBMI()
        return when {
            result < 18.5 -> resources.getString(R.string.underweight)
            result < 25 -> resources.getString(R.string.normal)
            result < 30 -> resources.getString(R.string.overweight)
            else -> resources.getString(R.string.obese)
        }
    }

    fun getLongDescription(): String {
        val result = bmi.countBMI()
        return when {
            result < 18.5 -> resources.getString(R.string.underweight_description)
            result < 25 -> resources.getString(R.string.normal_description)
            result < 30 -> resources.getString(R.string.overweight_description)
            else -> resources.getString(R.string.obese_description)
        }
    }

    fun getColor(): Int {
        val result = bmi.countBMI()
        return when {
            result < 18.5 -> resources.getColor(R.color.persianGreen)
            result < 25 -> resources.getColor(R.color.lapisLazuli)
            result < 30 -> resources.getColor(R.color.pompeianRed)
            else -> resources.getColor(R.color.flamboyantViolet)
        }
    }

    fun getImperialUnits(): Boolean {
        return imperialUnits
    }

    fun setImperialUnits(units: Boolean) {
        if(units != imperialUnits) {
            imperialUnits = units

            if(imperialUnits) {
                bmi = BMIFromLbsInch(0, 0)
            }
            else {
                bmi = BMIFromKgCm(0, 0)
            }
        }
    }

    fun toBundle(): Bundle {
        return bundleOf("imperialUnits" to imperialUnits,
            "mass" to bmi.mass,
            "height" to bmi.height)
    }

    fun setResources(resources: Resources) {
        this.resources = resources
    }
}