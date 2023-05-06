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


class gasEntries : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gasEntryList: ArrayList<gasDetails>
    private lateinit var backbtn:ImageButton

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_entries)

        backbtn = findViewById(R.id.gentries_backbtn)
        backbtn.setOnClickListener {
            val intent = Intent(this, gasCalculate::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.historyList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        gasEntryList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("gascalculate").get().addOnSuccessListener {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val gentry: gasDetails? = data.toObject(gasDetails::class.java)
                    if (gentry != null) {
                        gasEntryList.add(gentry)
                    }

                }
                recyclerView.adapter = GasAdapter(gasEntryList)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}
