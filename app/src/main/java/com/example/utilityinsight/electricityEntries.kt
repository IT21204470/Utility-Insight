package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class electricityEntries : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var entryList: ArrayList<Entries>

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_entries)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityHome::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        entryList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("eCalculations").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val entry: Entries? = data.toObject(Entries::class.java)
                    if (entry != null) {
                        entryList.add(entry)
                    }
                }
                recyclerView.adapter = MyAdapter(entryList)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }