package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bmi_info.*

class BmiInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_info)

        val stateBundle = intent.getBundleExtra("state")

        infoBmiResult.text = intent.getStringExtra("result")
        infoBmiCategory.text = intent.getStringExtra("category")
        infoBmiDescription.text = intent.getStringExtra("description")

        // TODO: get default color
        infoBmiResult.setTextColor(intent.getIntExtra("color", 0xFFFFFF))
        infoBmiCategory.setTextColor(intent.getIntExtra("color", 0xFFFFFF))
    }
}
