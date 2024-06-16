package com.smartdriver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val TEXT_AMAN = "Aman, tidak mengantuk"
val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

class MainViewModel: ViewModel(), ValueEventListener {

    val data = MutableLiveData<List<DataItem>>()
    val first = data.map { if(it.isEmpty()) null else it[0] }
    val firstN = data.map { if(it.isEmpty()) null else it.subList(0, Math.min(6, it.size)) }
    val status = MutableLiveData("")
    val lastConnectTime = MutableLiveData(SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date()))

    init {
        FirebaseDatabase.getInstance()
            .getReference("drowsiness")
            .addValueEventListener(this)

        FirebaseDatabase.getInstance()
            .getReference("last_uptime")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lastConnectTime.value = snapshot.getValue<String>()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
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