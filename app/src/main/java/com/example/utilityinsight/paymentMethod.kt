package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class paymentMethod : AppCompatActivity() {
    private lateinit var checkoutbutton: Button
    private lateinit var enterbill: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityPayment::class.java)
            startActivity(intent)
        }

        val rdGroup = findViewById<RadioGroup>(R.id.radiogroup)
        enterbill = findViewById(R.id.textView40)
        checkoutbutton = findViewById(R.id.to_checkout)

        val billAmount = intent.getStringExtra("billAmount")
        enterbill.text = "$billAmount LKR"

        rdGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.express, R.id.paypal -> {
                    Toast.makeText(this, "Not yet available", Toast.LENGTH_SHORT).show()
                    checkoutbutton.setOnClickListener{
                        Toast.makeText(this, "Choose an available payment method", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    Toast.makeText(this, findViewById<RadioButton>(checkedId).text, Toast.LENGTH_SHORT).show()
                    checkoutbutton.setOnClickListener {
                        val selectedButtonId = rdGroup.checkedRadioButtonId
                        if (selectedButtonId != -1) {
                            val checkoutbutton = findViewById<RadioButton>(selectedButtonId)
                            val intent = Intent(this, paymentDetails::class.java)
                            intent.putExtra("billAmount", billAmount)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


    }
}