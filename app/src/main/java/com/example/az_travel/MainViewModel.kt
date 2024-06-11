package com.example.az_travel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainViewModel: ViewModel(), ValueEventListener {
    val data = MutableLiveData<List<DataItem>>()
    val first = data.map { it[0] }

    init {
        FirebaseDatabase.getInstance()
            .getReference("drowsiness")
            .addValueEventListener(this)
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        val newList = mutableListOf<DataItem>()
        for(item in snapshot.children) {
            newList.add(item.getValue<DataItem>()!!)
        }
        newList.sortByDescending { it.timestamp }
        data.value = newList
    }

    override fun onCancelled(error: DatabaseError) {}

}