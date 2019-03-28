package com.example.bmi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bmi.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        aboutButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.about_me_toast), Toast.LENGTH_SHORT).show()
        }
    }
}
