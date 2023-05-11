package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase







class gasUpdate : AppCompatActivity() {

    // declare the views and data source
    private lateinit var gaccnumber: EditText
    private lateinit var gdays: EditText
    private lateinit var gunits: EditText
    private lateinit var gbtnCal: Button
    private lateinit var btnSub:Button
    private lateinit var userID:TextView


    private var db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_update)

        val backButton = findViewById<ImageButton>(R.id.ug_cal_page_back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, gas_home::class.java)
            startActivity(intent)
        }


        //initializing variables
        gaccnumber = findViewById(R.id.ug_acc_number)
        gdays = findViewById(R.id.ug_number_of_days)
        gunits = findViewById(R.id.ug_number_of_units)
        gbtnCal = findViewById(R.id.ug_home_calculate_btn)
        btnSub = findViewById(R.id.ug_home_store)
        userID = findViewById(R.id.uid)

        userID.visibility = View.INVISIBLE


        gaccnumber.text = intent.getStringExtra("AccountNo").toString().toEditable()
        gdays.text = intent.getStringExtra("Days").toString().toEditable()
        gunits.text = intent.getStringExtra("Units").toString().toEditable()
        userID.text = intent.getStringExtra("UID").toString().toEditable()

        gbtnCal.setOnClickListener {

            val gasunitText = gunits.text.toString().trim()
            val gasdaysText = gdays.text.toString().trim()
            val gasaccountNumber = gaccnumber.toString().trim()

            if (gasunitText.isEmpty() || gasdaysText.isEmpty() || gasaccountNumber.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val gasunit: Int
            gasunit = gasunitText.toInt()



            val gasdays: Int
            gasdays = gasdaysText.toInt()

            var gnormalcharge = 0
            var gfixedcharge = 0
            var gtotal = 0

            if (gasunit < 25) {
                gfixedcharge = 100
                gnormalcharge = gasunit * gasdays
                gtotal = gfixedcharge + gnormalcharge
            } else if (gasunit < 30) {
                gfixedcharge = 200
                gnormalcharge = gasunit * gasdays
                gtotal = gfixedcharge + gnormalcharge
            } else if (gasunit < 40) {
                gfixedcharge = 400
                gnormalcharge = gasunit * gasdays
                gtotal = gfixedcharge + gnormalcharge
            } else if (gasunit < 50) {
                gfixedcharge = 650
                gnormalcharge = gasunit * gasdays
                gtotal = gfixedcharge + gnormalcharge
            } else if (gasunit < 75) {
                gfixedcharge = 1000
                gnormalcharge = gasunit * gasdays
                gtotal = gfixedcharge + gnormalcharge
            } else {
                gfixedcharge = 1600
                gnormalcharge = (gasunit * gasdays)
                gtotal = gfixedcharge + gnormalcharge
            }

        }


        btnSub.setOnClickListener {

            val accountNumber = gaccnumber.text.toString().trim()
            val numberOfDays = gdays.text.toString().trim()
            val numberOfUnits = gunits.text.toString().trim()
            val userId = userID.text.toString()

            val gasupdateMap = mapOf(
                "AccountNo" to accountNumber,
                "Days" to numberOfDays,
                "Units" to numberOfUnits,
                "UID" to userId
            )

            db.collection("gascalculate").document(userId).update(gasupdateMap)

            Toast.makeText(this,"Successfully Edited", Toast.LENGTH_SHORT).show()

            val i = Intent(this, gasEntries::class.java)
            startActivity(i)
            finish()
        }

    }


    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}