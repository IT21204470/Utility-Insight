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

        checkoutbutton.setOnClickListener {
            val selectedButtonId = rdGroup.checkedRadioButtonId
            if (selectedButtonId != -1) {
                val checkoutbutton = findViewById<RadioButton>(selectedButtonId)
                Toast.makeText(this, checkoutbutton.text, Toast.LENGTH_SHORT).show()
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