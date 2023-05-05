package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class unsuccessfulMessage : AppCompatActivity() {
    private lateinit var homebutton: Button
    private lateinit var closebtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unsuccessful_message)

        homebutton = findViewById(R.id.previous)
        closebtn = findViewById(R.id.close)

        homebutton.setOnClickListener {
            val i = Intent(this, electricityPayment::class.java)
            startActivity(i)
            finish()

        }

        closebtn.setOnClickListener {
            val i = Intent(this, electricityHome::class.java)
            startActivity(i)
            finish()

        }
    }
}