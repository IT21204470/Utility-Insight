package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class paymentDetails : AppCompatActivity() {
    private lateinit var paynowbutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)

        paynowbutton = findViewById(R.id.to_checkout)

        paynowbutton.setOnClickListener {
            val i = Intent(this, successfulMessage::class.java)
            startActivity(i)
            finish()

        }
    }
}