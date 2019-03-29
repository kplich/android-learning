package com.example.bmi.activities.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.R
import com.example.bmi.logic.bmi.BmiRecord

class ItemHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.history_item, parent, false)) {

    private val bmiResult: TextView = itemView.findViewById(R.id.itemBmiResult)
    private val bmiCategory: TextView = itemView.findViewById(R.id.itemBmiCategory)
    private val itemDate: TextView = itemView.findViewById(R.id.itemDate)
    private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)

    fun bind(record: BmiRecord) {
        bmiResult.text = record.result
        bmiCategory.text = record.description
        itemDate.text = record.date

        // TODO: uncommenting this results in a ResourceNotFound exception
        //itemImage.setImageResource(record.pictureId)

        val color = record.color

        bmiResult.setTextColor(color)
        bmiCategory.setTextColor(color)
    }
}