package com.smartdriver

import androidx.recyclerview.widget.DiffUtil

data class DataItem(
    var status: String = "",
    var timestamp: String = "",
    var value: Double = 0.0,
)

class DataItemCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.status == newItem.status &&
                oldItem.timestamp == newItem.timestamp &&
                oldItem.value == newItem.value
    }
}