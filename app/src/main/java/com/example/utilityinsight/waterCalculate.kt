package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.view.View
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class waterCalculate : AppCompatActivity() {

    private lateinit var waccnumber: EditText
    private lateinit var wdays: EditText
    private lateinit var wunits: EditText
    private lateinit var waccname: EditText
    private lateinit var btncal: Button
    private lateinit var btnventries: Button
    private lateinit var progressbar: ProgressBar
    val charges = arrayOf("Fixed Charge(Units)","0-25 = 100.00(LKR)", "26-30 = 200.00(LKR)","31-40 = 400.00(LKR)","41-50 = 650.00(LKR)","51-75 = 1000.00(LKR)","Above 75 = 1600.00(LKR)")
    private lateinit var totcal: TextView


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_calculate)



        val backButton = findViewById<ImageButton>(R.id.w_cal_page_back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, waterHome::class.java)
            startActivity(intent)
        }



        //assigning variables to id
        waccnumber = findViewById(R.id.w_acc_number)
        waccname = findViewById(R.id.w_acc_name)
        wdays = findViewById(R.id.w_number_of_days)
        wunits = findViewById(R.id.w_number_of_units)
        btncal = findViewById(R.id.w_home_calculate_btn)
        btnventries = findViewById(R.id.w_home_store)
        totcal = findViewById(R.id.viewtotal)
        progressbar = findViewById(R.id.wProgressBar)

        progressbar.visibility = View.INVISIBLE

        btncal.setOnClickListener {

            val waterunitText = wunits.text.toString().trim()
            val waterdaysText = wdays.text.toString().trim()
            val wateraccountNumber = waccnumber.toString().trim()
            val wateraccountName = waccname.toString().trim()

            // Check if any of the fields are empty
            if (waterunitText.isEmpty() || waterdaysText.isEmpty() || wateraccountNumber.isEmpty() || wateraccountName.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener }

            val waterunit: Int
            try {
                waterunit = waterunitText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val waterdays: Int
            try {
                waterdays = waterdaysText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for days", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            var wnormalcharge = 0
            var wfixedcharge = 0
            var wtotal = 0

            if (waterunit < 25) {
                wfixedcharge = 100
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 30) {
                wfixedcharge = 200
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 40) {
                wfixedcharge = 400
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 50) {
                wfixedcharge = 650
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else if (waterunit < 75) {
                wfixedcharge = 1000
                wnormalcharge = waterunit * waterdays
                wtotal = wfixedcharge + wnormalcharge
            } else {
                wfixedcharge = 1600
                wnormalcharge = (waterunit * waterdays)
                wtotal = wfixedcharge + wnormalcharge
            }

            totcal.append("Total Payable Amount: $wtotal\n")
            totcal.append("Bill Period: $waterdays\n")
            totcal.append("Fixed Charge: $wfixedcharge")

        }

            btnventries.setOnClickListener{

                progressbar.visibility = View.VISIBLE

                val accountNumber = waccnumber.text.toString().trim()
                val accountName = waccname.text.toString().trim()
                val numberOfDays = wdays.text.toString().trim()
                val numberOfUnits = wunits.text.toString().trim()
                val wtotamount = totcal.text.toString().trim()


                val userMap = hashMapOf(
                    "Account Number" to accountNumber,
                    "Account Name" to accountName,
                    "Number of days" to numberOfDays,
                    "Number of units" to numberOfUnits,
                    "Total Amount" to wtotamount

                    )

                db.collection("wcalculate").document().set(userMap)
                    .addOnSuccessListener {
                        waccnumber.text.clear()
                        waccname.text.clear()
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