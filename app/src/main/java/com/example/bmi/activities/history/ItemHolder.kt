package com.example.bmi.activities.history

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.R
import com.example.bmi.logic.bmi.BmiRecord
import java.math.BigDecimal
import java.text.SimpleDateFormat

class ItemHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.history_item, parent, false)) {

    private val bmiResult: TextView = itemView.findViewById(R.id.itemBmiResult)
    private val bmiCategory: TextView = itemView.findViewById(R.id.itemBmiCategory)
    private val itemDate: TextView = itemView.findViewById(R.id.itemDate)
    private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)

    fun bind(record: String) {
        val strings = record.split(",")

        bmiResult.text = strings[0]
        bmiCategory.text = strings[1]
        itemDate.text = strings[2]
    }

    fun bind(record: BmiRecord, resources: Resources) {
        bmiResult.text = record.bmiResult.toString()
        bmiCategory.text = record.bmiCategory.getShortDescription(resources)
        itemDate.text = SimpleDateFormat("dd.MM.yyyy").format(record.date)
        itemImage.setImageResource(record.bmiCategory.pictureId)
    }
}