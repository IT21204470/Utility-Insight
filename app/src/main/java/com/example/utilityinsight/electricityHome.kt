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

        val myCardView2 = findViewById<CardView>(R.id.cardview2)
        myCardView2.setOnClickListener {
            val intent = Intent(this, electricityPayment::class.java)
            startActivity(intent)
        }

        val myCardView3 = findViewById<CardView>(R.id.cardview3)
        myCardView3.setOnClickListener {
            val intent = Intent(this, addConnection::class.java)
            startActivity(intent)
        }

        val myCardView4 = findViewById<CardView>(R.id.cardview4)
        myCardView4.setOnClickListener {
            val intent = Intent(this, electricityEntries::class.java)
            startActivity(intent)
        }

        val myCardView5 = findViewById<CardView>(R.id.cardview5)
        myCardView5.setOnClickListener {
            val intent = Intent(this, electricityTips::class.java)
            startActivity(intent)
        }

        val myCardView6 = findViewById<CardView>(R.id.cardview6)
        myCardView6.setOnClickListener {
            val intent = Intent(this, displayNotifications::class.java)
            startActivity(intent)
        }

    }
}