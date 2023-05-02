package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class electricityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_home)

        val myCardView = findViewById<CardView>(R.id.cardview1)
        myCardView.setOnClickListener {
            val intent = Intent(this, electricityCalculate::class.java)
            startActivity(intent)
        }

    }
}