package com.example.bmi.logic.state

import android.content.res.Resources
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.bmi.R
import com.example.bmi.logic.bmi.BMI
import java.math.BigDecimal
import java.math.RoundingMode

class AppState {
    companion object {
        const val UNITS_KEY = "imperialUnits"
        const val INVALID_STATE_KEY = "invalidState"
        const val MASS_KEY = "mass"
        const val HEIGHT_KEY = "height"
    }

    private lateinit var resources: Resources

    private var imperialUnits: Boolean = false
    private var bmi = BMI(0, 0)
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
                                else BigDecimal.valueOf(bmi.countBMI(imperialUnits)).setScale(2, RoundingMode.HALF_UP)

    fun getMassDescription(): String {
        return if(imperialUnits) {
            resources.getString(R.string.mass_description_lbs)
        } else {
            resources.getString(R.string.mass_description_kg)
        }
    }

    fun getHeightDescription(): String {
        return if(imperialUnits) {
            resources.getString(R.string.height_description_inch)
        } else {
            resources.getString(R.string.height_description_cm)
        }
    }

    fun getShortDescription(): String {
        return if(isInInvalidState) {
            resources.getString(R.string.empty_text)
        } else {
            val result = bmi.countBMI(imperialUnits)
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
            val result = bmi.countBMI(imperialUnits)
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
            ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        } else {
            val result = bmi.countBMI(imperialUnits)
            when {
                result < 18.5 -> ResourcesCompat.getColor(resources, R.color.persianGreen, null)
                result < 25 -> ResourcesCompat.getColor(resources, R.color.lapisLazuli, null)
                result < 30 -> ResourcesCompat.getColor(resources, R.color.pompeianRed, null)
                else -> ResourcesCompat.getColor(resources, R.color.flamboyantViolet, null)
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
        isInInvalidState = true
    }

    fun toBundle(): Bundle {
        val resultBundle = Bundle()

        resultBundle.putBoolean(UNITS_KEY, imperialUnits)
        resultBundle.putBoolean(INVALID_STATE_KEY, isInInvalidState)

        if(!isInInvalidState) {
            resultBundle.putInt(MASS_KEY, bmi.mass)
            resultBundle.putInt(HEIGHT_KEY, bmi.height)
        }

        return resultBundle
    }

    fun fromBundle(bundle: Bundle) {
        imperialUnits = bundle.getBoolean(UNITS_KEY)
        isInInvalidState = bundle.getBoolean(INVALID_STATE_KEY)

        if(!isInInvalidState) {
            bmi.height = bundle.getInt(HEIGHT_KEY)
            bmi.mass = bundle.getInt(MASS_KEY)
        }
    }

    fun setResources(resources: Resources) {
        this.resources = resources
    }
}