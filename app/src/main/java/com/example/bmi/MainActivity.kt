package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.bmi.logic.state.AppState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val state = AppState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        state.setResources(resources)

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

            //update state
            state.setMassAndHeight(mass, height)

            updateTextViews()
        }

        bmiInfoButton.setOnClickListener {
            if (bmiResult.text.toString() != "") {
                val intent = Intent(this, BmiInfo::class.java)
                intent.putExtra("result", state.getBmi().toString())
                intent.putExtra("category", state.getShortDescription())
                intent.putExtra("description", state.getLongDescription())
                intent.putExtra("color", state.getColor())
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBundle("state", state.toBundle())
        outState?.putString("result", bmiResult.text.toString())
        outState?.putString("category", bmiCategory.text.toString())
        outState?.putInt("color", bmiResult.currentTextColor)
        outState?.putBoolean("imperial", state.getImperialUnits())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val stateBundle = savedInstanceState!!.getBundle("state")
        val mass = stateBundle!!.getInt("mass")
        val height = stateBundle.getInt("height")
        val foundImperialUnits = stateBundle.getBoolean("imperialUnits")

        state.setImperialUnits(foundImperialUnits)
        state.setMassAndHeight(mass, height)

        updateTextViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        menu?.findItem(R.id.aboutMeUnits)?.isChecked = state.getImperialUnits()
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
                state.setImperialUnits(!state.getImperialUnits())
                item.isChecked = state.getImperialUnits()

                updateTextViews()

                return true
            }
            else -> true
        }
    }

    private fun updateTextViews() {
        massDescription.text = state.getMassDescription()
        heightDescription.text = state.getHeightDescription()

        bmiResult.text = state.getBmi().toString()
        bmiCategory.text = state.getShortDescription()

        bmiResult.setTextColor(state.getColor())
        bmiCategory.setTextColor(state.getColor())
        bmiInfoButton.background.setTint(state.getColor())
    }
}
