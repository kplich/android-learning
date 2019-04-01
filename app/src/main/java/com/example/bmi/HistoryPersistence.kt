package com.example.bmi

import android.content.SharedPreferences
import com.example.bmi.logic.bmi.BmiRecord

class HistoryPersistence {
    companion object {
        const val HISTORY_PREFERENCES_KEY = "history key"
        private const val ITEMS_KEY = "history"
        private const val NUMBER_OF_ITEMS = 10
        private const val EMPTY_STRING = ""
        private const val LIST_DELIMITER = ";"

        fun addEntry(record: BmiRecord, preferences: SharedPreferences) {
            val string = preferences.getString(ITEMS_KEY, EMPTY_STRING)!!
            val history = stringToList(string)
                .take(NUMBER_OF_ITEMS - 1)
                .toMutableList()

            history.add(0, record)

            with(preferences.edit()) {
                putString(ITEMS_KEY, listToString(history))
                apply()
            }
        }

        fun getEntries(preferences: SharedPreferences): List<BmiRecord> {
            val string = preferences.getString(ITEMS_KEY, EMPTY_STRING)!!
            return stringToList(string)
        }

        fun isEmpty(preferences: SharedPreferences): Boolean {
            val string = preferences.getString(ITEMS_KEY, EMPTY_STRING)!!
            return stringToList(string).isEmpty()
        }

        fun clearEntries(preferences: SharedPreferences) {
            with(preferences.edit()) {
                putString(ITEMS_KEY, EMPTY_STRING)
                apply()
            }
        }

        private fun listToString(list: List<BmiRecord>): String {
            val sb = StringBuilder()

            if (list.isEmpty()) {
                sb.append("")
            } else {
                for (i in 0 until list.size - 1) {
                    sb.append(list[i].toString())
                    sb.append(LIST_DELIMITER)
                }
                sb.append(list.last().toString())
            }

            return sb.toString()
        }

        private fun stringToList(string: String): List<BmiRecord> {
            return if (string.isEmpty()) emptyList() else string.split(LIST_DELIMITER).map { s -> BmiRecord.fromString(s) }
        }
    }
}