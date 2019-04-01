package com.example.bmi

import com.example.bmi.logic.bmi.BMI
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun from_valid_data_return_BMI() {
        val actualBMI = BMI(65, 170, false).countBMI()
        assertEquals(22.491, actualBMI, 0.001)
    }

    @Test
    fun from_other_valid_data_return_BMI() {
        val actualBMI = BMI(80, 190, false).countBMI()
        assertEquals(22.161, actualBMI, 0.001)
    }
}
