package com.example.bmi

import java.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.bmi.logic.BMICategory
import com.example.bmi.logic.BMIFromKgCm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countButton.setOnClickListener {
            //TODO: some validation methods?
            // read mass
            var mass: Int
            try {
                mass = massInput.text.toString().toInt()
            }
            catch (e: NumberFormatException) {
                massInput.error = getString(R.string.mass_input_error)
                mass = -1
            }

            if(mass == 0) {
                massInput.error = getString(R.string.mass_neq_zero)
                mass = -1
            }

            // read height
            var height: Int
            try {
                height = heightInput.text.toString().toInt()
            }
            catch (e: NumberFormatException) {
                heightInput.error = getString(R.string.height_input_error)
                height = -1
            }
            if(height == 0) {
                heightInput.error = getString(R.string.height_neq_zero)
                height = -1
            }

            // calculate BMI
            val bmiObject = BMIFromKgCm(mass, height)
            try {
                val calculatedBMI = BigDecimal(bmiObject.countBMI())

                val calculatedCategory = bmiObject.getCategory()

                bmiResult.text = String.format(calculatedBMI.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                bmiCategory.text = when (calculatedCategory) {
                    BMICategory.UNDERWEIGHT -> getString(R.string.underweight)
                    BMICategory.NORMAL -> getString(R.string.normal)
                    BMICategory.OVERWEIGHT -> getString(R.string.overweight)
                    BMICategory.OBESE -> getString(R.string.obese)
                }

                bmiResult.setTextColor(
                    ContextCompat.getColor(applicationContext,
                        when (calculatedCategory) {
                            BMICategory.UNDERWEIGHT -> R.color.persianGreen
                            BMICategory.NORMAL -> R.color.lapisLazuli
                            BMICategory.OVERWEIGHT -> R.color.pompeianRed
                            BMICategory.OBESE -> R.color.flamboyantViolet
                        }
                    )
                )

                bmiCategory.setTextColor(
                    ContextCompat.getColor(applicationContext,
                        when (calculatedCategory) {
                            BMICategory.UNDERWEIGHT -> R.color.persianGreen
                            BMICategory.NORMAL -> R.color.lapisLazuli
                            BMICategory.OVERWEIGHT -> R.color.pompeianRed
                            BMICategory.OBESE -> R.color.flamboyantViolet
                        }
                    )
                )

            }
            catch (e: Exception) {
                bmiResult.text = ""
                bmiCategory.text = ""
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("result", bmiResult.text.toString())
        outState?.putString("category", bmiCategory.text.toString())
        outState?.putInt("color", bmiResult.currentTextColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        bmiResult.text = savedInstanceState?.getString("result").toString()
        bmiCategory.text = savedInstanceState?.getString("category").toString()

        val color = savedInstanceState?.getInt("color")
        if (color != null) {
            bmiResult.setTextColor(color)
            bmiCategory.setTextColor(color)
        }
    }
}
