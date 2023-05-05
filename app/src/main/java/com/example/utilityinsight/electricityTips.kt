package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class electricityTips : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_tips)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityHome::class.java)
            startActivity(intent)
        }
    }
}