package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenu : AppCompatActivity() {

    private lateinit var electricitybtn:Button
    private lateinit var waterbtn:Button
    private lateinit var gasbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        electricitybtn = findViewById(R.id.btnElectricity)
        electricitybtn.setOnClickListener {
            val electIntent = Intent(this, electricityHome::class.java)
            startActivity(electIntent)
        }


        waterbtn = findViewById(R.id.btnWater)
        waterbtn.setOnClickListener {
            val waterIntent = Intent(this, waterHome::class.java)
            startActivity(waterIntent)
        }


        gasbtn = findViewById(R.id.btnGas)

        gasbtn.setOnClickListener {
            val gasIntent = Intent(this, userProfile::class.java)
            startActivity(gasIntent)
        }

    }
}