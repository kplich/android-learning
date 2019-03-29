package com.example.bmi.logic.state

import android.content.res.Resources
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.bmi.R
import com.example.bmi.logic.bmi.BMI
import com.example.bmi.logic.bmi.BmiRecord
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class AppState {
    companion object {
        const val UNITS_KEY = "usingImperialUnits"
        const val INVALID_STATE_KEY = "invalidState"
        const val MASS_KEY = "mass"
        const val HEIGHT_KEY = "height"

        const val INVALID_STATE_MSG = "Operation isn't possible because there's no valid input to the BMI calculator."
    }

    private var bmi = BMI(0, 0, false)
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

    fun getMassTitle(resources: Resources): String {
        return if(bmi.usingImperialUnits) {
            resources.getString(R.string.mass_description_lbs)
        } else {
            resources.getString(R.string.mass_description_kg)
        }
    }

    fun getHeightTitle(resources: Resources): String {
        return if(bmi.usingImperialUnits) {
            resources.getString(R.string.height_description_inch)
        } else {
            resources.getString(R.string.height_description_cm)
        }
    }

    fun getShortDescription(resources: Resources): String {
        return if(isInInvalidState) {
            resources.getString(R.string.empty_text)
        } else {
            bmi.getCategory().getShortDescription(resources)
        }
    }

    fun getLongDescription(resources: Resources): String {
        return if(isInInvalidState) {
            resources.getString(R.string.empty_text)
        } else {
            bmi.getCategory().getLongDescription(resources)
        }
    }

    fun getColor(resources: Resources): Int {
        return if(isInInvalidState) {
            ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        } else {
            bmi.getCategory().getColor(resources)
        }
    }

    fun getPictureId(): Int {
        return if(isInInvalidState) {
            throw IllegalStateException(INVALID_STATE_MSG)
        }
        else {
            bmi.getCategory().pictureId
        }
    }

    fun getImperialUnits(): Boolean {
        return bmi.usingImperialUnits
    }

    fun isValid(): Boolean {
        return !isInInvalidState
    }

    fun changeUnits() {
        bmi.usingImperialUnits = !bmi.usingImperialUnits
        isInInvalidState = true
    }

    fun toBundle(): Bundle {
        val resultBundle = Bundle()

        resultBundle.putBoolean(UNITS_KEY, bmi.usingImperialUnits)
        resultBundle.putBoolean(INVALID_STATE_KEY, isInInvalidState)

        if(!isInInvalidState) {
            resultBundle.putInt(MASS_KEY, bmi.mass)
            resultBundle.putInt(HEIGHT_KEY, bmi.height)
        }

        return resultBundle
    }

    fun fromBundle(bundle: Bundle) {
        bmi.usingImperialUnits = bundle.getBoolean(UNITS_KEY)
        isInInvalidState = bundle.getBoolean(INVALID_STATE_KEY)

        if(!isInInvalidState) {
            bmi.height = bundle.getInt(HEIGHT_KEY)
            bmi.mass = bundle.getInt(MASS_KEY)
        }
    }

    fun getRecord(resources: Resources): BmiRecord {
        return BmiRecord(
            getBmi().toString(),
            getShortDescription(resources),
            SimpleDateFormat("dd.MM.yyyy").format(Date()),
            getPictureId(),
            getColor(resources)
        )
    }
}