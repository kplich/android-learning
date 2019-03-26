package com.example.bmi

import android.content.Intent
import java.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.bmi.logic.BMICategory
import com.example.bmi.logic.BMIFromKgCm
import com.example.bmi.logic.BMIFromLbsInch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var imperialUnits = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBmiButton.setOnClickListener {
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
            val bmiObject = if(imperialUnits) BMIFromLbsInch(mass, height) else BMIFromKgCm(mass, height)
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

                val resultingColor = ContextCompat.getColor(applicationContext,
                    when (calculatedCategory) {
                        BMICategory.UNDERWEIGHT -> R.color.persianGreen
                        BMICategory.NORMAL -> R.color.lapisLazuli
                        BMICategory.OVERWEIGHT -> R.color.pompeianRed
                        BMICategory.OBESE -> R.color.flamboyantViolet
                    }
                )

                bmiResult.setTextColor(resultingColor)
                bmiCategory.setTextColor(resultingColor)
                bmiInfoButton.background.setTint(resultingColor)
            }
            catch (e: Exception) {
                bmiResult.text = ""
                bmiCategory.text = ""
            }

        }

        bmiInfoButton.setOnClickListener {
            if (bmiResult.text.toString() != "") {
                val intent = Intent(this, BmiInfo::class.java)
                intent.putExtra("bmiResult", bmiResult.text.toString())
                intent.putExtra("bmiCategory", bmiCategory.text.toString())
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("result", bmiResult.text.toString())
        outState?.putString("category", bmiCategory.text.toString())
        outState?.putInt("color", bmiResult.currentTextColor)
        outState?.putBoolean("imperial", imperialUnits)
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

        val foundImperial = savedInstanceState?.getBoolean("imperial")
        if (foundImperial != null) {
            imperialUnits = foundImperial

            // TODO: these guys repeat
            if(imperialUnits) {
                massDescription.text = getString(R.string.mass_description_lbs)
                heightDescription.text = getString(R.string.height_description_inch)
            }
            else {
                massDescription.text = getString(R.string.mass_description_kg)
                heightDescription.text = getString(R.string.height_description_cm)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        menu?.findItem(R.id.aboutMeUnits)?.isChecked = imperialUnits
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.aboutMeItem -> {
                val intent = Intent(this, AboutMe::class.java)
                startActivity(intent)
                true
            }
            R.id.aboutMeUnits -> {
                imperialUnits = !imperialUnits
                item.isChecked = imperialUnits

                if(imperialUnits) {
                    massDescription.text = getString(R.string.mass_description_lbs)
                    heightDescription.text = getString(R.string.height_description_inch)
                }
                else {
                    massDescription.text = getString(R.string.mass_description_kg)
                    heightDescription.text = getString(R.string.height_description_cm)
                }

                bmiResult.text = getString(R.string.empty_text)
                bmiCategory.text = getString(R.string.empty_text)

                return true
            }
            else -> true
        }
    }
}
