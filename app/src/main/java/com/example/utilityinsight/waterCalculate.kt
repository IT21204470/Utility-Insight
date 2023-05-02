package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class waterCalculate : AppCompatActivity() {

    private lateinit var waccnumber: EditText
    private lateinit var wdays: EditText
    private lateinit var wunits: EditText
    private lateinit var btncal: Button
    val charges = arrayOf("Fixed Charge(Units)","0-25 = 100.00(LKR)", "26-30 = 200.00(LKR)","31-40 = 400.00(LKR)","41-50 = 650.00(LKR)","51-75 = 1000.00(LKR)","Above 75 = 1600.00(LKR)")



    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_calculate)

        //assigning variables to id
        waccnumber = findViewById(R.id.w_acc_number)
        wdays = findViewById(R.id.w_number_of_days)
        wunits = findViewById(R.id.w_number_of_units)
        btncal = findViewById(R.id.w_home_calculate_btn)

        //implementing spinner
        val spinner = findViewById<Spinner>(R.id.w_fixed_charge)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, charges)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(
                    applicationContext,
                    "Selected category is " + charges[p2],
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")


            }
        }


        btncal.setOnClickListener {
            val accountNumber = waccnumber.text.toString().trim()
            val numberOfDays = wdays.text.toString().trim()
            val numberOfUnits = wunits.text.toString().trim()

            val userMap = hashMapOf(
                "Account Number" to accountNumber,
                "Number of days" to numberOfDays,
                "Number of units" to numberOfUnits,
            )

            db.collection("wcalculate").document().set(userMap)
                .addOnSuccessListener {
                    waccnumber.text.clear()
                    wdays.text.clear()
                    wunits.text.clear()
                    Toast.makeText(this, "Calculate successfully",Toast.LENGTH_SHORT).show()
                    val i = Intent(this, waterHome::class.java)
                    startActivity(i)
                    finish()
                }

        }

    }
}