package com.example.bmi


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.bmi.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EverythingIsDisplayedCorrectlyTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val massDescriptionView = onView(withId(R.id.mainMassTitle))
        massDescriptionView.check(matches(withText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.mass_description_kg)
        )))
        massDescriptionView.check(matches(isDisplayed()))

        val massInputField = onView(withId(R.id.mainMassInputField))
        massInputField.check(matches(withHint(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.mass_fill_in_hint)
        )))
        massInputField.check(matches(isDisplayed()))

        val heightDescriptionView = onView(withId(R.id.mainHeightTitle))
        heightDescriptionView.check(matches(withText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.height_description_cm)
        )))
        heightDescriptionView.check(matches(isDisplayed()))

        val heightInputField = onView(withId(R.id.mainHeightInputField))
        heightInputField.check(matches(withHint(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.height_fill_in_hint)
        )))
        heightInputField.check(matches(isDisplayed()))

        val countBmiButton = onView(withId(R.id.mainCountButton))
        countBmiButton.check(matches(isDisplayed()))
        countBmiButton.check(matches(withText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.count_button_text))
        ))
    }
}
