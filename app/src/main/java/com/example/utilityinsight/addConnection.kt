package com.example.utilityinsight

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class addConnection : AppCompatActivity() {

    val servicetype = arrayOf("Choose Service", "Electricity", "Water", "Gas")

    private lateinit var etname: EditText
    private lateinit var etnumber: EditText
    private lateinit var etpremises: EditText
    private lateinit var btnsave: Button
    private lateinit var btndecline: Button
    private lateinit var progressBar: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_connection)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityHome::class.java)
            startActivity(intent)
        }

        val cancelbtn = findViewById<Button>(R.id.decline_btn)
        cancelbtn.setOnClickListener{
            Toast.makeText(this, "Process cancelled", Toast.LENGTH_SHORT).show()
            val i = Intent(this, electricityHome::class.java)
            startActivity(i)
        }

        //implementing spinner
        val serspinner = findViewById<Spinner>(R.id.category)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servicetype)
        serspinner.adapter = arrayAdapter
        serspinner.setSelection(0, false)
        serspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext, "Selected Service: " + servicetype[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please select an item", Toast.LENGTH_SHORT).show()
            }
        }

        //assigning variables to id
        etname = findViewById(R.id.acc_name)
        etnumber = findViewById(R.id.acc_no)
        etpremises = findViewById(R.id.premisesID)
        btnsave = findViewById(R.id.save_btn)
        btndecline = findViewById(R.id.decline_btn)
        progressBar = findViewById(R.id.wProgressBar)

        progressBar.visibility = View.INVISIBLE

        btnsave.setOnClickListener {

            val userid = UUID.randomUUID().toString()
            val newspinner = serspinner.selectedItem.toString().trim()
            val accountName = etname.text.toString().trim()
            val accountNo = etnumber.text.toString().trim()
            val preID = etpremises.text.toString().trim()

            if (newspinner == servicetype[0] || userid.isEmpty() || accountName.isEmpty() || accountNo.isEmpty() || preID.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidAccountNumber(accountNo)) {
                Toast.makeText(applicationContext, "Account number should have 10 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Toast.makeText(this, "Account number verified", Toast.LENGTH_SHORT).show()
            }

            progressBar.visibility = View.VISIBLE

            val accountNumber = accountNo.trim()

            val userMap = hashMapOf(
                "userID" to userid,
                "service" to newspinner,
                "accountName" to accountName,
                "accountNumber" to accountNo,
                "premisesID" to preID,
            )


            db.collection("accounts").document(userid).set(userMap)
                .addOnSuccessListener {
                    val accountNumberMap = hashMapOf(
                        "accountNumber" to accountNumber
                    )
                    db.collection("accountNumbers").add(accountNumberMap)
                        .addOnSuccessListener {
                            etname.text.clear()
                            etnumber.text.clear()
                            etpremises.text.clear()
                            progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show()
                            val i = Intent(this, electricityHome::class.java)
                            i.putExtra("accountName", accountName)
                            startActivity(i)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error adding account number to accountNumbers collection", e)
                            Toast.makeText(this, "Error adding account number", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error adding account number to accountNumbers collection", e)
                    Toast.makeText(this, "Error adding account number", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun isValidAccountNumber(accountNumber: String): Boolean {
        val regex = "\\d{10}".toRegex()
        return accountNumber.matches(regex)
    }
}