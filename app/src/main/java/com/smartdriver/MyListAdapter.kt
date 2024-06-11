package com.smartdriver

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartdriver.databinding.ListItemBinding

class MyListAdapter() : ListAdapter<DataItem, MyListAdapter.ViewHolder>(DataItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            binding.text.text = Html.fromHtml(String.format(
                "<b>Status</b>: %s<br/><b>Timestamp</b>: %s<br/><b>Value</b>: %.2f",
                data.status,
                data.timestamp,
                data.value
            ))
        }
    }

}