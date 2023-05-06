package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class waterEntries : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var entryList: ArrayList<Records>

    private var db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_entries)



        val wEntriesbackButton = findViewById<ImageButton>(R.id.w_entries_back_btn)
        wEntriesbackButton.setOnClickListener {
            val intent = Intent(this, waterHome::class.java)
            startActivity(intent)
        }



        recyclerView = findViewById(R.id.w_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        entryList = arrayListOf()



        db.collection("wcalculate").get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                if (querySnapshot != null) {
                    for (document in querySnapshot.documents) {
                        val entry: Records? = document.toObject(Records::class.java)
                        if (entry != null) {
                            entryList.add(entry)
                        }
                    }
                    recyclerView.adapter = WaterMyAdapter(entryList,this,db)
                }
            }

            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

    }
}