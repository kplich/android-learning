package com.example.bmi.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bmi.HistoryPersistence
import com.example.bmi.R
import com.example.bmi.activities.history.History
import com.example.bmi.logic.state.AppState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    companion object {
        const val RESULT_KEY = "result"
        const val CATEGORY_KEY = "category"
        const val DESCRIPTION_KEY = "description"
        const val PICTURE_KEY = "picture id"
        const val COLOR_KEY = "color"
        const val DEFAULT_COLOR_KEY = "default color"
        const val STATE_BUNDLE_KEY = "state"
        const val NULL_STATE_BUNDLE_MSG = "Null state bundle!"
    }

    private val state = AppState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainCountButton.setOnClickListener {
            val mass = validateInput(mainMassInputField, getString(R.string.mass_input_error), Pair({ result -> result != 0}, getString(
                R.string.mass_neq_zero
            )))
            val height = validateInput(mainHeightInputField, getString(R.string.height_input_error), Pair({ result -> result != 0}, getString(
                R.string.height_neq_zero
            )))
            state.setMassAndHeight(mass, height) // update state
            updateUI() // update interface

            if(state.isValid()) {
                HistoryPersistence.addEntry(
                    state.getRecord(resources),
                    prefs()
                )
            }

            invalidateOptionsMenu()
        }

        mainInfoButton.setOnClickListener {
            val intent = Intent(this, BmiInfo::class.java)
            intent.putExtra(RESULT_KEY, state.getBmi().toString())
            intent.putExtra(CATEGORY_KEY, state.getShortDescription(resources))
            intent.putExtra(DESCRIPTION_KEY, state.getLongDescription(resources))
            intent.putExtra(PICTURE_KEY, state.getPictureId())
            intent.putExtra(COLOR_KEY, state.getColor(resources))
            intent.putExtra(
                DEFAULT_COLOR_KEY, ContextCompat.getColor(this,
                    R.color.colorPrimary
                ))

            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBundle(STATE_BUNDLE_KEY, state.toBundle())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val restoredBundle = savedInstanceState?.getBundle(STATE_BUNDLE_KEY)
        if (restoredBundle != null) {
            state.fromBundle(restoredBundle)
        }
        else {
            throw IllegalStateException(NULL_STATE_BUNDLE_MSG)
        }

        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        menu?.findItem(R.id.unitsMenuItem)?.isChecked = state.getImperialUnits()
        menu!!.findItem(R.id.historyMenuItem).isEnabled = !HistoryPersistence.isEmpty(prefs())
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.findItem(R.id.historyMenuItem).isEnabled = !HistoryPersistence.isEmpty(prefs())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.aboutMeMenuItem -> {
                val intent = Intent(this, AboutMe::class.java)
                startActivity(intent)
                true
            }
            R.id.unitsMenuItem -> {
                state.changeUnits()
                item.isChecked = state.getImperialUnits()

                mainMassInputField.setText(R.string.empty_text)
                mainHeightInputField.setText(R.string.empty_text)

                updateUI()

                return true
            }
            R.id.historyMenuItem -> {
                Toast.makeText(this, "go to history", Toast.LENGTH_SHORT)

                val intent = Intent(this, History::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    /**
     * Method for re-calculating all texts and colors viewed on screen.
     * Uses information from application state.
     */
    private fun updateUI() {
        mainMassTitle.text = state.getMassTitle(resources)
        mainHeightTitle.text = state.getHeightTitle(resources)

        mainBmiResult.text = state.getBmi()?.toString() ?: getString(R.string.empty_text)
        mainBmiCategory.text = state.getShortDescription(resources)

        mainBmiResult.setTextColor(state.getColor(resources))
        mainBmiCategory.setTextColor(state.getColor(resources))

        if(state.isValid()) {
            mainInfoButton.visibility = VISIBLE
            mainInfoButton.isEnabled = true
            mainInfoButton.background.setTint(state.getColor(resources))
        }
        else {
            mainInfoButton.visibility = INVISIBLE
            mainInfoButton.isEnabled = false
            mainInfoButton.background.setTint(state.getColor(resources))
        }


    }

    /**
     * Method for validating input from a field with given conditions.
     * @param field TextField from which a value is read
     * @param errorMessage general error message displayed when reading input fails
     * @param validationRules pairs comprised of functions validating the result and corresponding error messages
     * if validation fails
     * @return number conforming to given conditions or null when input doesn't conform to constraints
     */
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

    // TODO: repeated function!
    private fun prefs(): SharedPreferences {
        return getSharedPreferences(HistoryPersistence.HISTORY_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }
}
