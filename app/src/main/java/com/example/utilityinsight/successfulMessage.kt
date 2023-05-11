package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class successfulMessage : AppCompatActivity() {
    private lateinit var backtohome: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_message)

        backtohome = findViewById(R.id.previous)

        backtohome.setOnClickListener {
            val i = Intent(this, waterHome::class.java)
            startActivity(i)
            finish()
        }

    }
}