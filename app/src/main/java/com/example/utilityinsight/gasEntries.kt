package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class gasEntries : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_entries)

        val backbtn = findViewById<ImageButton>(R.id.gentries_backbtn)
        backbtn.setOnClickListener {
            val intent = Intent(this, gasCalculate::class.java)
            startActivity(intent)
        }
    }
}