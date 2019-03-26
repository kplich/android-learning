package com.example.bmi.logic.state

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.bmi.logic.bmi.BMI
import com.example.bmi.logic.bmi.BMICategory
import com.example.bmi.logic.bmi.BMIFromKgCm
import com.example.bmi.logic.bmi.BMIFromLbsInch
import java.math.BigDecimal
import java.math.RoundingMode

class AppState {

    private var imperialUnits: Boolean = false
    private var bmi: BMI = BMIFromKgCm(0, 0)

    //TODO: how the hell should I handle this???
    private var category: BMICategory = BMICategory.NA

    fun setMassAndHeight(mass: Int, height: Int) {
        bmi.mass = mass
        bmi.height = height
        category = bmi.getCategory()
    }

    fun getBmi(): BigDecimal? =
        if(category == BMICategory.NA) null
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
        return category.shortDescription
    }

    fun getLongDescription(): String {
        return category.longDescription
    }

    fun getColor(): Int {
        return category.color
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

            category = bmi.getCategory()
        }
    }

    fun toBundle(): Bundle {
        return bundleOf("imperialUnits" to imperialUnits,
            "mass" to bmi.mass,
            "height" to bmi.height)
    }


}