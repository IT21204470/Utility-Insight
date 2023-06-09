package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class waterCalculate : AppCompatActivity() {

    val fixedprice = arrayOf("Select Fixed Charge", "0-25 = 100.00(LKR)", "26-30 = 200.00(LKR)", "31-40 = 400.00(LKR)", "41-50 = 650.00(LKR)", "51-75 = 1000.00(LKR)", "Above 75 = 1600.00(LKR)")

    private lateinit var waccnumber: EditText
    private lateinit var wdays: EditText
    private lateinit var wunits: EditText
    private lateinit var waccname: EditText
    private lateinit var btncal: Button
    private lateinit var btnventries: Button
    private lateinit var progressbar: ProgressBar
    private lateinit var totcal: TextView
    private lateinit var billdb: TextView


    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_calculate)



        val backButton = findViewById<ImageButton>(R.id.w_cal_page_back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, waterHome::class.java)
            startActivity(intent)
        }

        //implementing spinner
        val fspinner = findViewById<Spinner>(R.id.w_fixed_charge)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, fixedprice)
        fspinner.adapter = arrayAdapter
        fspinner.setSelection(0, false)
        fspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext, "Fixed Price: " + fixedprice[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please select an item", Toast.LENGTH_SHORT).show()
            }
        }

        val waterCalculateValidation = waterCalculateValidation()

        //assigning variables to id
        waccnumber = findViewById(R.id.w_acc_number)
        waccname = findViewById(R.id.w_acc_name)
        wdays = findViewById(R.id.w_number_of_days)
        wunits = findViewById(R.id.w_number_of_units)
        btncal = findViewById(R.id.w_home_calculate_btn)
        btnventries = findViewById(R.id.w_home_store)
        totcal = findViewById(R.id.viewtotal)
        progressbar = findViewById(R.id.wProgressBar)
        billdb = findViewById(R.id.textView62)

        btnventries.visibility = View.INVISIBLE
        totcal.visibility = View.INVISIBLE
        billdb.visibility = View.INVISIBLE
        progressbar.visibility = View.INVISIBLE

        btncal.setOnClickListener {

            val waterspinner = fspinner.selectedItem.toString().trim()
            val waterunitText = wunits.text.toString().trim()
            val waterdaysText = wdays.text.toString().trim()
            val wateraccountNumber = waccnumber.text.toString().trim()
            val wateraccountName = waccname.text.toString().trim()

            // Check if any of the fields are empty
            if (waterspinner == fixedprice[0] || waterunitText.isEmpty() || waterdaysText.isEmpty() || wateraccountNumber.isEmpty() || wateraccountName.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                billdb.visibility = View.INVISIBLE
                totcal.visibility = View.INVISIBLE
                btnventries.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            if (!isValidAccountNumber(wateraccountNumber)) {
                Toast.makeText(applicationContext, "Account number invalid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
            Toast.makeText(this, "Account number verified", Toast.LENGTH_SHORT).show()
            }

            totcal.visibility = View.VISIBLE
            btnventries.visibility = View.VISIBLE

            val waterunit: Int
            try {
                waterunit = waterunitText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for units", Toast.LENGTH_SHORT)
                    .show()
                btnventries.visibility = View.INVISIBLE
                totcal.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            val waterdays: Int
            try {
                waterdays = waterdaysText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Invalid input for days", Toast.LENGTH_SHORT)
                    .show()
                btnventries.visibility = View.INVISIBLE
                totcal.visibility = View.INVISIBLE
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

            totcal.text = "Bill Period: $waterdays days\nImport Charge: $wnormalcharge LKR\nFixed Charge: $wfixedcharge LKR\nTotal Bill Amount: $wtotal LKR"
            billdb.text = "$wtotal LKR"

        }

        btnventries.setOnClickListener{
            val  userID = UUID.randomUUID().toString()
            totcal.visibility = View.INVISIBLE
            progressbar.visibility = View.VISIBLE

            val waterspinner = fspinner.selectedItem.toString().trim()
            val accountNumber = waccnumber.text.toString().trim()
            val accountName = waccname.text.toString().trim()
            val numberOfDays = wdays.text.toString().trim()
            val numberOfUnits = wunits.text.toString().trim()
            val totalAmount = billdb.text.toString().trim()

            if (waterCalculateValidation.waterCalculateValidationFields(accountNumber,accountName,numberOfDays,numberOfUnits)){
                val userMap = hashMapOf(
                    "accountname" to accountName,
                    "accountnumber" to accountNumber,
                    "numberofdays" to numberOfDays,
                    "numberofunits" to numberOfUnits,
                    "fixedCharge" to waterspinner,
                    "totalamount" to totalAmount,
                    "userIDD" to userID
                )

                db.collection("wcalculate").document(userID).set(userMap)
                    .addOnSuccessListener {
                        waccnumber.text.clear()
                        waccname.text.clear()
                        wdays.text.clear()
                        wunits.text.clear()
                        Toast.makeText(this, "Added successfully",Toast.LENGTH_SHORT).show()
                        val i = Intent(this, waterEntries::class.java)
                        startActivity(i)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                    }
            }
            else{
                Toast.makeText(this,"Please fill in all the fields",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun isValidAccountNumber(accountNumber: String): Boolean {
        val regex = "\\d{16}".toRegex()
        return accountNumber.matches(regex)
    }

}