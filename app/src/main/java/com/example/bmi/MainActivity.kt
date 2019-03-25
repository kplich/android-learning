package com.example.bmi

import java.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.bmi.logic.BMICategory
import com.example.bmi.logic.BMIFromKgCm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countButton.setOnClickListener {
            // read mass
            var mass: Int
            try {
                mass = massInput.text.toString().toInt()
            }
            catch (e: NumberFormatException) {
                massInput.error = getString(R.string.mass_input_error)
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
            }
            catch (e: Exception) {
                bmiResult.text = ""
                bmiCategory.text = ""
            }

        }
    }
}
