package com.example.bmi.logic.bmi

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.example.bmi.R

enum class BmiCategory(
    private val categoryId: Int,
    private val descriptionId: Int,
    private val colorId: Int,
    val pictureId: Int
) {
    NA(R.string.empty_text, R.string.empty_text, R.color.colorPrimary, R.drawable.default_pic),
    UNDERWEIGHT(R.string.underweight, R.string.underweight_description, R.color.verdigras, R.drawable.underweight_pic),
    NORMAL(R.string.normal, R.string.normal_description, R.color.lapisLazuli, R.drawable.normal_pic),
    OVERWEIGHT(R.string.overweight, R.string.overweight_description, R.color.pompeianRed, R.drawable.overweight_pic),
    OBESE(R.string.obese, R.string.obese_description, R.color.flamboyantViolet, R.drawable.obese_pic);

    fun getShortDescription(resources: Resources): String = resources.getString(categoryId)

    fun getLongDescription(resources: Resources): String = resources.getString(descriptionId)

    fun getColor(resources: Resources) = ResourcesCompat.getColor(resources, colorId, null)
}