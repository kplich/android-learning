package com.example.bmi.logic.state

import android.content.res.Resources
import android.os.Bundle
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
    private var isInInvalidState = true

    fun setMassAndHeight(mass: Int?, height: Int?) {
        if(mass == null || height == null) {
            isInInvalidState = true
        }
        else {
            bmi.mass = mass
            bmi.height = height
            isInInvalidState = false
        }
    }

    fun getBmi(): BigDecimal? = if (isInInvalidState) null
                                else BigDecimal.valueOf(bmi.countBMI()).setScale(2, RoundingMode.HALF_UP)

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
        return if(isInInvalidState) {
            resources.getString(R.string.empty_text)
        } else {
            val result = bmi.countBMI()
            when {
                result < 18.5 -> resources.getString(R.string.underweight)
                result < 25 -> resources.getString(R.string.normal)
                result < 30 -> resources.getString(R.string.overweight)
                else -> resources.getString(R.string.obese)
            }
        }
    }

    fun getLongDescription(): String {
        return if(isInInvalidState) {
            resources.getString(R.string.empty_text)
        } else {
            val result = bmi.countBMI()
            return when {

                result < 18.5 -> resources.getString(R.string.underweight_description)
                result < 25 -> resources.getString(R.string.normal_description)
                result < 30 -> resources.getString(R.string.overweight_description)
                else -> resources.getString(R.string.obese_description)
            }
        }
    }

    fun getColor(): Int {
        return if(isInInvalidState) {
            resources.getColor(R.color.colorPrimary)
        } else {
            val result = bmi.countBMI()
            when {
                // TODO: deal with getColor
                result < 18.5 -> resources.getColor(R.color.persianGreen)
                result < 25 -> resources.getColor(R.color.lapisLazuli)
                result < 30 -> resources.getColor(R.color.pompeianRed)
                else -> resources.getColor(R.color.flamboyantViolet)
            }
        }
    }

    fun getImperialUnits(): Boolean {
        return imperialUnits
    }

    fun isValid(): Boolean {
        return !isInInvalidState
    }

    fun changeUnits() {
        imperialUnits = !imperialUnits

        if(imperialUnits) {
            bmi = BMIFromLbsInch(0, 0)
        }
        else {
            bmi = BMIFromKgCm(0, 0)
        }

        isInInvalidState = true
    }

    fun toBundle(): Bundle {
        val resultBundle = Bundle()

        resultBundle.putBoolean("imperialUnits", imperialUnits)
        resultBundle.putBoolean("invalidState", isInInvalidState)

        if(!isInInvalidState) {
            resultBundle.putInt("mass", bmi.mass)
            resultBundle.putInt("height", bmi.height)
        }

        return resultBundle
    }

    fun fromBundle(bundle: Bundle) {
        imperialUnits = bundle.getBoolean("imperialUnits")
        isInInvalidState = bundle.getBoolean("invalidState")

        if(!isInInvalidState) {
            bmi = if(imperialUnits) BMIFromLbsInch(bundle.getInt("mass"), bundle.getInt("height"))
                  else BMIFromKgCm(bundle.getInt("mass"), bundle.getInt("height"))
        }
    }

    fun setResources(resources: Resources) {
        this.resources = resources
    }
}