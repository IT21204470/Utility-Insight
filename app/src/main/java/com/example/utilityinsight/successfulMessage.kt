package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class successfulMessage : AppCompatActivity() {
    private lateinit var donebutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_message)

        donebutton = findViewById(R.id.previous)

        donebutton.setOnClickListener {
            val i = Intent(this, electricityHome::class.java)
            startActivity(i)
            finish()
        }

    }
}