package com.example.bmi.activities.history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.HistoryPersistence
import com.example.bmi.R

class History : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById<RecyclerView>(R.id.history_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@History)
            adapter = HistoryAdapter(HistoryPersistence.getEntries(prefs()))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.clear_history -> {
                //clear history
                HistoryPersistence.clearEntries(prefs())

                //update the view
                recyclerView = findViewById<RecyclerView>(R.id.history_recycler_view).apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@History)
                    adapter = HistoryAdapter(HistoryPersistence.getEntries(prefs()))
                }

                true
            }
            else -> true
        }
    }

    private fun prefs(): SharedPreferences {
        return getSharedPreferences(HistoryPersistence.HISTORY_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }
}
