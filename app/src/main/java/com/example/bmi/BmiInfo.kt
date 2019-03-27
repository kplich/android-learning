package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_bmi_info.*

class BmiInfo : AppCompatActivity() {
    companion object {
        const val DEFAULT_COLOR = 0xFFFFFF
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)



        infoBmiResult.text = intent.getStringExtra(MainActivity.RESULT_KEY)
        infoBmiCategory.text = intent.getStringExtra(MainActivity.CATEGORY_KEY)
        infoBmiDescription.text = intent.getStringExtra(MainActivity.DESCRIPTION_KEY)

        val primaryColor = intent.getIntExtra(MainActivity.DEFAULT_COLOR_KEY, DEFAULT_COLOR)
        infoBmiResult.setTextColor(intent.getIntExtra(MainActivity.COLOR_KEY, primaryColor))
        infoBmiCategory.setTextColor(intent.getIntExtra(MainActivity.COLOR_KEY, primaryColor))

        bmiInfoImage.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                intent.getIntExtra(MainActivity.PICTURE_KEY, R.drawable.default_pic)
            )
        )
    }
}
