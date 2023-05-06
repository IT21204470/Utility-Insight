package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class gasTips : AppCompatActivity() {
    private lateinit var gbackbtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_tips)

        gbackbtn = findViewById(R.id.back_btn)
        gbackbtn.setOnClickListener {
            val intent = Intent(this, gas_home::class.java)
            startActivity(intent)
        }
    }
}