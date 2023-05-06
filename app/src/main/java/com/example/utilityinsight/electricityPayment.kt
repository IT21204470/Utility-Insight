package com.example.utilityinsight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class electricityPayment : AppCompatActivity() {

    private lateinit var spinner3: Spinner
    private lateinit var bamount: EditText
    private lateinit var email: EditText
    private lateinit var enterbtn: Button
    private lateinit var progressBar4: ProgressBar

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_payment)

        val backButton = findViewById<ImageButton>(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, electricityHome::class.java)
            startActivity(intent)
        }

        //assigning variables to id
        spinner3 = findViewById(R.id.spinner3)
        bamount = findViewById(R.id.amount)
        email = findViewById(R.id.email)
        enterbtn = findViewById(R.id.proceed)
        progressBar4 = findViewById(R.id.progressBar4)

        progressBar4.visibility = View.INVISIBLE

        fetchAccountNames()

        enterbtn.setOnClickListener{

            val userid = UUID.randomUUID().toString()
            val newspinner = spinner3.selectedItem.toString().trim()
            val billamount = bamount.text.toString().trim()
            val eaddress = email.text.toString().trim()

            // Check if any of the fields are empty
            if (newspinner.isEmpty() || billamount.isEmpty() || eaddress.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the email address is valid
            if (!eaddress.contains("@gmail.com")) {
                Toast.makeText(applicationContext, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar4.visibility = View.VISIBLE

            val userMap = hashMapOf(
                "userID" to userid,
                "account" to newspinner,
                "billAmount" to billamount,
                "emailAddress" to eaddress,
            )

            db.collection("payments").document(userid).set(userMap)
                .addOnSuccessListener {
                    bamount.text.clear()
                    email.text.clear()

                    Toast.makeText(this, "Payment Initialized", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, paymentMethod::class.java)
                    i.putExtra("billAmount", billamount)
                    startActivity(i)
                    finish()
                }
        }
    }
    private fun fetchAccountNames() {
        db.collection("accounts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val accountNames = mutableListOf<String>()
                for (document in querySnapshot) {
                    val accountName = document.getString("Account Name")
                    if (!accountName.isNullOrEmpty()) {
                        accountNames.add(accountName)
                    }
                }
                updateSpinner(accountNames)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    applicationContext,
                    "Failed to fetch account names: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateSpinner(accountNames: List<String>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, accountNames)
        arrayAdapter.insert("Choose Account", 0)
        spinner3.adapter = arrayAdapter
        spinner3.setSelection(0)
    }

}