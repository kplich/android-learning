package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.example.bmi.logic.state.AppState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val state = AppState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        state.setResources(resources)

        countBmiButton.setOnClickListener {
            // read mass
            val mass = validateInput(
                massInput,
                getString(R.string.mass_input_error),
                Pair({result -> result != 0}, getString(R.string.mass_neq_zero))
            )

            // read height
            val height = validateInput(
                heightInput,
                getString(R.string.height_input_error),
                Pair({result -> result != 0}, getString(R.string.height_neq_zero))
            )

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
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val restoredBundle = savedInstanceState?.getBundle("state")
        if (restoredBundle != null) {
            state.fromBundle(restoredBundle)
        }
        else {
            throw IllegalStateException("Null state bundle!")
        }

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
                state.changeUnits()
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

        bmiResult.text = state.getBmi()?.toString() ?: getString(R.string.empty_text)
        bmiCategory.text = state.getShortDescription()

        bmiResult.setTextColor(state.getColor())
        bmiCategory.setTextColor(state.getColor())
        bmiInfoButton.background.setTint(state.getColor())
    }

    private fun validateInput(field: EditText, errorMessage: String, vararg validationRules: Pair<(Int) -> (Boolean), String>): Int? {
        var result: Int? = null
        var ruleBroken = false

        try {
            result = field.text.toString().toInt()


            for(rule in validationRules) {
                if(!rule.first(result)) {
                    field.error = rule.second
                    ruleBroken = true
                    break
                }
            }
        } catch (e: NumberFormatException) {
            field.error = errorMessage
        }

        return if(ruleBroken) null else result
    }
}
