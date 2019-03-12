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

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bmiObject = BMIFromKgCm()



        countButton.setOnClickListener {
            val mass = Integer.valueOf(massInput.text.toString())
            val height = Integer.valueOf(heightInput.text.toString())

            bmiObject.mass = mass
            bmiObject.height = height

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
    }
}
