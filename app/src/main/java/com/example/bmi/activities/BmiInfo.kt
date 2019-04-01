package com.example.bmi.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bmi.R
import kotlinx.android.synthetic.main.activity_bmi_info.*

class BmiInfo : AppCompatActivity() {
    companion object {
        const val DEFAULT_COLOR = 0xFFFFFF
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)



        infoBmiResult.text = intent.getStringExtra(Main.RESULT_KEY)
        infoBmiCategory.text = intent.getStringExtra(Main.CATEGORY_KEY)
        infoBmiDescription.text = intent.getStringExtra(Main.DESCRIPTION_KEY)

        val primaryColor = intent.getIntExtra(
            Main.DEFAULT_COLOR_KEY,
            DEFAULT_COLOR
        )
        infoBmiResult.setTextColor(intent.getIntExtra(Main.COLOR_KEY, primaryColor))
        infoBmiCategory.setTextColor(intent.getIntExtra(Main.COLOR_KEY, primaryColor))

        infoBmiImage.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                intent.getIntExtra(
                    Main.PICTURE_KEY,
                    R.drawable.default_pic
                )
            )
        )
    }
}
