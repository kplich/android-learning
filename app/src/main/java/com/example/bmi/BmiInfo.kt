package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bmi_info.*

class BmiInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)

        infoBmiResult.text = intent.getStringExtra("bmiResult")
        infoBmiCategory.text = intent.getStringExtra("bmiCategory")
    }
}
