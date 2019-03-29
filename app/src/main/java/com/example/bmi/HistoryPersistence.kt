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

        fun addEntry(record: BmiRecord, preferences: SharedPreferences) {
            val history = preferences.getStringSet(ITEMS_KEY, mutableSetOf()).toMutableList()
            //history.sortDescending() //TODO: what here???
            //history = history.take(NUMBER_OF_ITEMS - 1).toMutableList()
            history.add(0, record.toString())

            with(preferences.edit()) {
                putStringSet(ITEMS_KEY, history.toMutableSet())
                apply() //TODO: what's going on here?
            }
        }

        fun getEntries(preferences: SharedPreferences): List<BmiRecord> {
            val stringSet = preferences.getStringSet(ITEMS_KEY, mutableSetOf())!!
            return stringSet.map{input -> BmiRecord.fromString(input)}.toMutableList()
        }

        fun isEmpty(preferences: SharedPreferences): Boolean {
            val set = preferences.getStringSet(ITEMS_KEY, mutableSetOf())
            return set!!.size == 0
        }

        fun clearEntries(preferences: SharedPreferences) {
            with(preferences.edit()) {
                putStringSet(ITEMS_KEY, mutableListOf<String>().toMutableSet())
                apply() //TODO: what's going on here?
            }
        }
    }
}