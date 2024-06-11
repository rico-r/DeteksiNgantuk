package com.smartdriver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

val TEXT_AMAN = "Aman, tidak mengantuk"

class MainViewModel: ViewModel(), ValueEventListener {

    val data = MutableLiveData<List<DataItem>>()
    val first = data.map { it[0] }
    val firstN = data.map { it.subList(0, 6) }
    val status = MutableLiveData("")

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