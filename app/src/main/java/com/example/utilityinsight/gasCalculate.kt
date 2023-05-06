package com.example.utilityinsight

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class gasCalculate : AppCompatActivity() {

    private lateinit var gaccountno: EditText
    private lateinit var gcalcdate: EditText
    private lateinit var gcalunits: EditText
    private lateinit var gcalcdays: EditText

    private lateinit var gcalcbtn: Button
    private lateinit var gcaloutouttxt: TextView
    private lateinit var gcaloutoutans: TextView

    private var db = Firebase.firestore


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gas_calculate)


        val gasViewEntriesbtn = findViewById<Button>(R.id.g_viewEntries_btn)
        gasViewEntriesbtn.setOnClickListener {
            val intent = Intent(this, gasEntries::class.java)
            startActivity(intent)
        }

        //assigning variables to id
        gaccountno= findViewById(R.id.gas_calc_accnoinputx)
        gcalcdate= findViewById(R.id.gas_calc_date)
        gcalunits= findViewById(R.id.gas_unit_countinput)
        gcalcdays= findViewById(R.id.gas_days_count)
        gcalcbtn= findViewById(R.id.gas_cal_btn)
        gcaloutouttxt= findViewById(R.id.gas_calc_amount_txt)
        gcaloutoutans= findViewById(R.id.gas_calamount_output)

        gcalcbtn.setOnClickListener{

            val gasaccountno = gaccountno.text.toString().trim()
            val  gascalcdate= gcalcdate.text.toString().trim()
            val  gascalunits= gcalunits.text.toString().trim()
            val  gascalcdays= gcalcdays.text.toString().trim()

            // Check if any of the fields are empty
            if (gasaccountno.isEmpty() || gascalcdate.isEmpty() || gascalunits.isEmpty() || gascalcdays.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener }

            val gasunit: Int
            try {
                gasunit = gascalunits.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val gasdays: Int
            try {
                gasdays = gascalcdays.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for days", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            //calculation
            var gtotalamount = 0

            gtotalamount = gasunit * gasdays * 13

            gcaloutoutans.text = "Rs:$gtotalamount"

            //hashmap

            val gasuserMap = hashMapOf(
                "AccountNo" to gasaccountno,
                "Date" to gascalcdate,
                "Units" to gascalunits,
                "Days" to gascalcdays
            )

            val userId = UUID.randomUUID().toString()

           db.collection("gascalculate").document(userId).set(gasuserMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Successfully added!", Toast.LENGTH_SHORT).show()
                    gaccountno.text.clear()
                    gcalcdate.text.clear()
                    gcalunits.text.clear()
                    gcalcdays.text.clear()
                }

                .addOnFailureListener{
                    Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
                }

        }

    }
}