package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class successfulMessage : AppCompatActivity() {
    private lateinit var backbutton: Button
    private lateinit var donebutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_message)

        backbutton = findViewById(R.id.previous)

        backbutton.setOnClickListener {
            val i = Intent(this, paymentDetails::class.java)
            startActivity(i)
            finish()

        }

        donebutton = findViewById(R.id.close)

        donebutton.setOnClickListener {
            val i = Intent(this, electricityHome::class.java)
            startActivity(i)
            finish()

        }

    }
}