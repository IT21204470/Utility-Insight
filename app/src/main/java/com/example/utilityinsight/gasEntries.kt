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

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_entries)

        val gEntriesbackButton = findViewById<ImageButton>(R.id.gentries_backbtn)
        gEntriesbackButton.setOnClickListener {
            val intent = Intent(this, gas_home::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.historyList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        gasEntryList = arrayListOf()

//        db = FirebaseFirestore.getInstance()

        db.collection("gascalculate").get().addOnSuccessListener {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val gentry: gasDetails? = data.toObject(gasDetails::class.java)
                    if (gentry != null) {
                        gasEntryList.add(gentry)
                    }

                }
                recyclerView.adapter = GasAdapter(gasEntryList,this,db)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }


}
