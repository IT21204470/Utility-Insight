package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class successfulMessage : AppCompatActivity() {
    private lateinit var homebutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_message)

        homebutton = findViewById(R.id.previous)

        homebutton.setOnClickListener {
            val i = Intent(this, waterHome::class.java)
            startActivity(i)
            finish()

        }
    }
}