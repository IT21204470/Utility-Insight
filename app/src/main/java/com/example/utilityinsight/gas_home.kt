package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class gas_home : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_home)

        val gasCalCardView = findViewById<CardView>(R.id.gas_card_cal)
        gasCalCardView.setOnClickListener {
            val intent = Intent(this, gasCalculate::class.java)
            startActivity(intent)
        }
        val gasPayCardView = findViewById<CardView>(R.id.gas_card_pay)
        gasPayCardView.setOnClickListener {
            val intent = Intent(this, paymentMethod::class.java)
            startActivity(intent)
        }
        val gasAddCardView = findViewById<CardView>(R.id.gas_card_addcon)
        gasAddCardView.setOnClickListener {
            val intent = Intent(this, addConnection::class.java)
            startActivity(intent)
        }
        val gasTipsCardView = findViewById<CardView>(R.id.gas_card_tips)
        gasTipsCardView.setOnClickListener {
            val intent = Intent(this, gasTips::class.java)
            startActivity(intent)
        }
    }
}