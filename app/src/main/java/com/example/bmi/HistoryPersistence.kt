package com.example.bmi

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.bmi.logic.bmi.BmiRecord

class HistoryPersistence {
    companion object {
        const val HISTORY_PREFERENCES_KEY = "history key"
        private const val ITEMS_KEY = "items"
        private const val NUMBER_OF_ITEMS = 10

        fun saveBmi(record: BmiRecord, preferences: SharedPreferences) {
            val history = preferences.getStringSet(ITEMS_KEY, mutableSetOf())

            var mlhistory = history.toMutableList()
            mlhistory.sortDescending() //TODO: what here???
            mlhistory = mlhistory.take(NUMBER_OF_ITEMS - 1).toMutableList()
            mlhistory.add(0, record.toString())

            with(preferences.edit()) {
                putStringSet(ITEMS_KEY, mlhistory.toMutableSet())
                apply() //TODO: what's going on here?
            }
        }

        fun loadBmi(activity: Activity): MutableSet<String> {
            val sharedPrefs = activity.getSharedPreferences(HISTORY_PREFERENCES_KEY, Context.MODE_PRIVATE)
            return sharedPrefs.getStringSet(HISTORY_PREFERENCES_KEY, mutableSetOf())!!
        }

        fun isEmpty(activity: Activity): Boolean {
            val sharedPrefs = activity.getSharedPreferences(HISTORY_PREFERENCES_KEY, Context.MODE_PRIVATE)
            val set = sharedPrefs.getStringSet(HISTORY_PREFERENCES_KEY, mutableSetOf())
            return set!!.size == 0
        }
    }
}