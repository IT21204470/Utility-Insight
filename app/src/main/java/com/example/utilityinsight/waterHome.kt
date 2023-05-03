package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class waterHome : AppCompatActivity() {

    private lateinit var calculateusagebutton:Button
    private lateinit var paybillbutton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_home)

        calculateusagebutton = findViewById(R.id.w_home_calculate_btn)
        paybillbutton = findViewById(R.id.w_home_pay_bill)

        calculateusagebutton.setOnClickListener {
            val i = Intent(this, waterCalculate::class.java)
            startActivity(i)
            finish()

        }

        paybillbutton.setOnClickListener {
            val i = Intent(this, paymentMethod::class.java)
            startActivity(i)
            finish()
        }
    }
}