package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class gasListItems : AppCompatActivity() {

    private lateinit var gupdatebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gas_list_items)

        gupdatebtn = findViewById(R.id.gas_entries_updte)
        gupdatebtn.setOnClickListener {
            val intent = Intent(this, gasCalculate::class.java)
            startActivity(intent)
        }

    }
}