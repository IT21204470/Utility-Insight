package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class paymentDetails : AppCompatActivity() {
    private lateinit var paynowbutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        val paymetDetailsbackButton = findViewById<ImageButton>(R.id.back_btn)
        paymetDetailsbackButton.setOnClickListener {
            val intent = Intent(this, paymentMethod::class.java)
            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, paymentMethod::class.java)
            startActivity(intent)
        }

        paynowbutton = findViewById(R.id.to_checkout)

        paynowbutton.setOnClickListener {
            val i = Intent(this, successfulMessage::class.java)
            startActivity(i)
            finish()

        }
    }
}