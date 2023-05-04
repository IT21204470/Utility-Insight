package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class paymentMethod : AppCompatActivity() {
    private lateinit var checkoutbutton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        val paymetMethodbackButton = findViewById<ImageButton>(R.id.paymentmethod_back_btn)
        paymetMethodbackButton.setOnClickListener {
            val intent = Intent(this, waterHome::class.java)
            startActivity(intent)
        }

        val rdGroup = findViewById<RadioGroup>(R.id.radiogroup)

        checkoutbutton = findViewById(R.id.to_checkout)

        checkoutbutton.setOnClickListener(View.OnClickListener{
            val selectBtn:Int = rdGroup!!.checkedRadioButtonId
            val checkoutbutton = findViewById<RadioButton>(selectBtn)
            Toast.makeText(this,checkoutbutton.text,Toast.LENGTH_SHORT).show()
        })

        checkoutbutton.setOnClickListener {
            val i = Intent(this, paymentDetails::class.java)
            startActivity(i)
            finish()

        }
    }
}