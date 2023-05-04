package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class waterHome : AppCompatActivity() {


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_home)


        val waterCalCardView = findViewById<CardView>(R.id.w_cardview1)
        waterCalCardView.setOnClickListener {
            val intent = Intent(this, waterCalculate::class.java)
            startActivity(intent)
        }
        val waterPayCardView = findViewById<CardView>(R.id.w_cardview2)
        waterPayCardView.setOnClickListener {
            val intent = Intent(this, paymentMethod::class.java)
            startActivity(intent)
        }
        val waterAddCardView = findViewById<CardView>(R.id.w_cardview3)
        waterAddCardView.setOnClickListener {
            val intent = Intent(this, addConnection::class.java)
            startActivity(intent)
        }
        val waterTipsCardView = findViewById<CardView>(R.id.w_cardview4)
        waterTipsCardView.setOnClickListener {
            val intent = Intent(this, waterShowtips::class.java)
            startActivity(intent)
        }


    }
}




